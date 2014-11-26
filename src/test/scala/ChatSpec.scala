import com.flyberry.slack.HttpClient
import com.flyberry.slack.Methods.Chat
import org.joda.time.{DateTime, DateTimeZone}
import org.mockito.Mockito._
import org.scalatest.mock.MockitoSugar
import org.scalatest.{BeforeAndAfterEach, FlatSpec, Matchers}
import play.api.libs.json.Json

/*
 * Copyright (c) 2014 Flyberry Capital, LLC
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

class ChatSpec extends FlatSpec with MockitoSugar with Matchers with BeforeAndAfterEach {

   private val testApiKey = "TEST_API_KEY"
   private val testChannel = "C12345"
   private val testTs = "1416960000"
   private val testMessage = "This is a test."
   private val testUpdateMessage = "This is an update."

   private var mockHttpClient : HttpClient = _
   var chat : Chat = _

   override def beforeEach() {
      mockHttpClient = mock[HttpClient]

      when(mockHttpClient.post("chat.delete", Map(
         "channel" -> testChannel,
         "ts" -> testTs,
         "token" -> testApiKey
      )))
         .thenReturn(Json.parse(
         s"""
           |{
           |    "ok": true,
           |    "channel": "$testChannel",
           |    "ts": "$testTs"
           |}
         """.stripMargin))

      when(mockHttpClient.post("chat.postMessage", Map(
         "channel" -> testChannel,
         "token" -> testApiKey,
         "text" -> testMessage
      )))
         .thenReturn(Json.parse(
         s"""
           |{
           |    "ok": true,
           |    "channel": "$testChannel",
           |    "ts": "$testTs"
           |}
         """.stripMargin))

      when(mockHttpClient.post("chat.update", Map(
         "channel" -> testChannel,
         "ts" -> testTs,
         "token" -> testApiKey,
         "text" -> testUpdateMessage
      )))
         .thenReturn(Json.parse(
         s"""
           |{
           |    "ok": true,
           |    "channel": "$testChannel",
           |    "ts": "$testTs",
           |    "text": "$testUpdateMessage"
           |}
         """.stripMargin))

      chat = new Chat(mockHttpClient, testApiKey)
   }

   "Chat.delete()" should "delete a message when given a channel and timestamp" in {
      val response = chat.delete(testChannel, testTs)

      response.ok shouldBe true
      response.channel shouldBe testChannel
      response.ts shouldBe testTs
      response.date.isEqual(new DateTime(2014, 11, 26, 0, 0, DateTimeZone.UTC)) shouldBe true

      verify(mockHttpClient).post("chat.delete", Map("channel" -> testChannel, "ts" -> testTs, "token" -> testApiKey))
   }

   "Chat.delete()" should "delete a message when given a response object" in {
      val messageResponse = chat.postMessage(testChannel, testMessage)
      val response = chat.delete(messageResponse)

      response.ok shouldBe true
      response.channel shouldBe testChannel
      response.ts shouldBe testTs
      response.date.isEqual(new DateTime(2014, 11, 26, 0, 0, DateTimeZone.UTC)) shouldBe true

      verify(mockHttpClient).post("chat.delete", Map("channel" -> testChannel, "ts" -> testTs, "token" -> testApiKey))
   }

   "Chat.postMessage()" should "post a new message" in {
      val response = chat.postMessage(testChannel, testMessage)

      response.ok shouldBe true
      response.ts shouldBe testTs
      response.channel shouldBe testChannel

      verify(mockHttpClient).post("chat.postMessage", Map("channel" -> testChannel, "text" -> testMessage, "token" -> testApiKey))
   }

   "Chat.update()" should "update a message when given a channel, timestamp, and update text" in {
      val response = chat.update(testChannel, testTs, testUpdateMessage)

      response.ok shouldBe true
      response.channel shouldBe testChannel
      response.ts shouldBe testTs
      response.date.isEqual(new DateTime(2014, 11, 26, 0, 0, DateTimeZone.UTC)) shouldBe true

      verify(mockHttpClient).post(
         "chat.update",
         Map(
            "channel" -> testChannel,
            "ts" -> testTs,
            "token" -> testApiKey,
            "text" -> testUpdateMessage
         )
      )
   }

   "Chat.update()" should "update a message when given a response object and update text" in {
      val messageResponse = chat.postMessage(testChannel, testMessage)
      val response = chat.update(messageResponse, testUpdateMessage)

      response.ok shouldBe true
      response.channel shouldBe testChannel
      response.ts shouldBe testTs
      response.date.isEqual(new DateTime(2014, 11, 26, 0, 0, DateTimeZone.UTC)) shouldBe true

      verify(mockHttpClient).post(
         "chat.update",
         Map(
            "channel" -> testChannel,
            "ts" -> testTs,
            "token" -> testApiKey,
            "text" -> testUpdateMessage
         )
      )
   }
}
