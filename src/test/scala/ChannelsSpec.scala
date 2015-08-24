import com.flyberrycapital.slack.HttpClient
import com.flyberrycapital.slack.Methods.Channels
import org.joda.time.{DateTimeZone, DateTime}
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

class ChannelsSpec extends FlatSpec with MockitoSugar with Matchers with BeforeAndAfterEach {

   private val testApiKey = "TEST_API_KEY"

   private var mockHttpClient : HttpClient = _
   var channels : Channels = _

   override def beforeEach() {
      mockHttpClient = mock[HttpClient]
      when(mockHttpClient.get("channels.history", Map("channel" -> "C12345", "token" -> testApiKey)))
         .thenReturn(Json.parse(
         """
           |{
           |    "ok": true,
           |    "latest": "1358547726.000003",
           |    "messages": [
           |        {
           |            "type": "message",
           |            "ts": "1358546515.000008",
           |            "user": "U2147483896",
           |            "text": "Hello"
           |        },
           |        {
           |            "type": "message",
           |            "ts": "1358546515.000007",
           |            "user": "U2147483896",
           |            "text": "World",
           |            "is_starred": true
           |        },
           |        {
           |            "type": "something_else",
           |            "ts": "1358546515.000007",
           |            "wibblr": true
           |        }
           |    ],
           |    "has_more": true
           |}
         """.stripMargin))

      when(mockHttpClient.get("channels.history", Map("channel" -> "C12345", "latest" -> "1358546515.000007", "token" -> testApiKey)))
         .thenReturn(Json.parse(
         """
           |{
           |    "ok": true,
           |    "latest": "1358547726.000003",
           |    "messages": [
           |        {
           |            "type": "message",
           |            "ts": "1358546515.000008",
           |            "user": "U2147483896",
           |            "text": "Hello"
           |        },
           |        {
           |            "type": "message",
           |            "ts": "1358546515.000007",
           |            "user": "U2147483896",
           |            "text": "World",
           |            "is_starred": true
           |        }
           |    ],
           |    "has_more": false
           |}
         """.stripMargin))

      when(mockHttpClient.get("channels.list", Map("token" -> testApiKey)))
         .thenReturn(Json.parse(
         """
           |{
           |    "ok": true,
           |    "channels": [
           |        {
           |            "id": "C12345",
           |            "name": "testchannel1",
           |            "is_channel": true,
           |            "created": 1408475169,
           |            "creator": "U12345",
           |            "is_archived": false,
           |            "is_general": false,
           |            "is_member": true,
           |            "members": [
           |                "U12345",
           |                "U23456",
           |                "U34567"
           |            ],
           |            "topic": {
           |                "value": "",
           |                "creator": "",
           |                "last_set": "0"
           |            },
           |            "purpose": {
           |                "value": "a test channel",
           |                "creator": "U12345",
           |                "last_set": "1408475170"
           |            },
           |            "num_members": 3
           |        },
           |        {
           |            "id": "C23456",
           |            "name": "testchannel2",
           |            "is_channel": true,
           |            "created": 1408419457,
           |            "creator": "U12345",
           |            "is_archived": false,
           |            "is_general": false,
           |            "is_member": true,
           |            "members": [
           |                "U12345",
           |                "U23456",
           |                "U34567",
           |                "U45678",
           |                "U56789"
           |            ],
           |            "topic": {
           |                "value": "",
           |                "creator": "",
           |                "last_set": "0"
           |            },
           |            "purpose": {
           |                "value": "Hang out with developers here!",
           |                "creator": "U23456",
           |                "last_set": "1408419457"
           |            },
           |            "num_members": 5
           |        },
           |        {
           |            "id": "C34567",
           |            "name": "testchannel3",
           |            "is_channel": true,
           |            "created": 1408419260,
           |            "creator": "U23456",
           |            "is_archived": false,
           |            "is_general": true,
           |            "is_member": true,
           |            "members": [
           |                "U12345",
           |                "U23456",
           |                "U34567",
           |                "U45678",
           |                "U56789",
           |                "U67890"
           |            ],
           |            "topic": {
           |                "value": "",
           |                "creator": "",
           |                "last_set": "0"
           |            },
           |            "purpose": {
           |                "value": "This channel is for team-wide communication and announcements.",
           |                "creator": "",
           |                "last_set": "0"
           |            },
           |            "num_members": 6
           |        }
           |    ]
           |}
         """.stripMargin))

      when(mockHttpClient.get("channels.setTopic", Map("channel" -> "C12345", "topic" -> "Test Topic", "token" -> testApiKey)))
         .thenReturn(Json.parse(
         """
           |{
           |    "ok": true,
           |    "topic": "Test Topic"
           |}
         """.stripMargin))

      channels = new Channels(mockHttpClient, testApiKey)
   }

   "Channels.history()" should "make a call to channels.history and return the response in an ChannelHistoryResponse object" in {
      val response = channels.history("C12345")

      response.ok shouldBe true
      response.hasMore shouldBe true
      response.isLimited shouldBe false
      response.messages should have length 3

      val message = response.messages(0)

      message.messageType shouldBe "message"
      message.ts shouldBe "1358546515.000008"
      message.user.get shouldBe "U2147483896"
      message.text.get shouldBe "Hello"
      message.time.isEqual(new DateTime(2013, 1, 18, 22, 1, 55, DateTimeZone.UTC)) shouldBe true

      verify(mockHttpClient).get("channels.history", Map("channel" -> "C12345", "token" -> testApiKey))
   }

   "Channels.historyStream()" should "make repeated calls to channels.history and return a stream of messages" in {
      val messages = channels.historyStream("C12345").toList

      messages should have length 5

      verify(mockHttpClient).get("channels.history", Map("channel" -> "C12345", "latest" -> "1358546515.000007", "token" -> testApiKey))
   }

   "Channels.list()" should "list all channels available to a user" in {
      val response = channels.list()

      response.ok shouldBe true
      response.channels should have length 3

      val channel = response.channels(0)
      channel.id shouldBe "C12345"
      channel.name shouldBe "testchannel1"
      channel.created shouldBe 1408475169
      channel.creator shouldBe "U12345"
      channel.isArchived shouldBe false
      channel.isMember shouldBe true
      channel.members should have length 3
      channel.numMembers shouldBe 3
      (channel.purpose \ "value").as[String] shouldBe "a test channel"

      verify(mockHttpClient).get("channels.list", Map("token" -> testApiKey))
   }

   "Channels.setTopic()" should "make a call to channels.setTopic and return the response in an ChannelSetTopicResponse object" in {
      val response = channels.setTopic("C12345", "Test Topic")

      response.ok shouldBe true
      response.topic shouldBe "Test Topic"

      verify(mockHttpClient).get("channels.setTopic", Map("channel" -> "C12345", "topic" -> "Test Topic", "token" -> testApiKey))
   }

}
