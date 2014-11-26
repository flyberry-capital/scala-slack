import com.flyberry.slack.Exceptions._
import com.flyberry.slack.HttpClient
import org.scalatest.{PrivateMethodTester, BeforeAndAfterEach, FlatSpec, Matchers}
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

class HttpClientSpec extends FlatSpec with Matchers with BeforeAndAfterEach with PrivateMethodTester {

   val httpClient = new HttpClient()
   val checkResponse = PrivateMethod[String]('checkResponse)

   "checkResponse()" should "raise no error if \"ok\" is true" in {
      val response = Json.parse(
         """
           | { "ok" : true }
         """.stripMargin)

      noException should be thrownBy (httpClient invokePrivate checkResponse(response))
   }

   "checkResponse()" should "raise an InvalidAuthError if the invalid_auth error is received" in {
      val response = Json.parse(
         """
           | {
           |   "ok" : false,
           |   "error": "invalid_auth"
           | }
         """.stripMargin
      )

      an [InvalidAuthError] should be thrownBy (httpClient invokePrivate checkResponse(response))
   }

   "checkResponse()" should "raise a NotAuthedError if the not_authed error is received" in {
      val response = Json.parse(
         """
           | {
           |   "ok" : false,
           |   "error": "not_authed"
           | }
         """.stripMargin
      )

      a [NotAuthedError] should be thrownBy (httpClient invokePrivate checkResponse(response))
   }

   "checkResponse()" should "raise an AccountInactiveError if the account_inactive error is received" in {
      val response = Json.parse(
         """
           | {
           |   "ok" : false,
           |   "error": "account_inactive"
           | }
         """.stripMargin
      )

      an [AccountInactiveError] should be thrownBy (httpClient invokePrivate checkResponse(response))
   }

   "checkResponse()" should "raise a ChannelNotFoundError if the channel_not_found error is received" in {
      val response = Json.parse(
         """
           | {
           |   "ok" : false,
           |   "error": "channel_not_found"
           | }
         """.stripMargin
      )

      a [ChannelNotFoundError] should be thrownBy (httpClient invokePrivate checkResponse(response))
   }

   "checkResponse()" should "raise a NotInChannelError if the not_in_channel error is received" in {
      val response = Json.parse(
         """
           | {
           |   "ok" : false,
           |   "error": "not_in_channel"
           | }
         """.stripMargin
      )

      a [NotInChannelError] should be thrownBy (httpClient invokePrivate checkResponse(response))
   }

   "checkResponse()" should "raise a RateLimitedError if the rate_limited error is received" in {
      val response = Json.parse(
         """
           | {
           |   "ok" : false,
           |   "error": "rate_limited"
           | }
         """.stripMargin
      )

      a [RateLimitedError] should be thrownBy (httpClient invokePrivate checkResponse(response))
   }

   "checkResponse()" should "raise a NoMessageTextProvidedError if the no_text error is received" in {
      val response = Json.parse(
         """
           | {
           |   "ok" : false,
           |   "error": "no_text"
           | }
         """.stripMargin
      )

      an [NoMessageTextProvidedError] should be thrownBy (httpClient invokePrivate checkResponse(response))
   }

   "checkResponse()" should "raise a MessageTooLongError if the msg_too_long error is received" in {
      val response = Json.parse(
         """
           | {
           |   "ok" : false,
           |   "error": "msg_too_long"
           | }
         """.stripMargin
      )

      a [MessageTooLongError] should be thrownBy (httpClient invokePrivate checkResponse(response))
   }

   "checkResponse()" should "raise a MessageNotFoundError if the message_not_found error is received" in {
      val response = Json.parse(
         """
           | {
           |   "ok" : false,
           |   "error": "message_not_found"
           | }
         """.stripMargin
      )

      an [MessageNotFoundError] should be thrownBy (httpClient invokePrivate checkResponse(response))
   }

   "checkResponse()" should "raise an EditWindowClosedError if the edit_window_closed error is received" in {
      val response = Json.parse(
         """
           | {
           |   "ok" : false,
           |   "error": "edit_window_closed"
           | }
         """.stripMargin
      )

      an [EditWindowClosedError] should be thrownBy (httpClient invokePrivate checkResponse(response))
   }

   "checkResponse()" should "raise an CantUpdateMessageError if the cant_update_message error is received" in {
      val response = Json.parse(
         """
           | {
           |   "ok" : false,
           |   "error": "cant_update_message"
           | }
         """.stripMargin
      )

      an [CantUpdateMessageError] should be thrownBy (httpClient invokePrivate checkResponse(response))
   }

   "checkResponse()" should "raise a CantDeleteMessageError if the cant_delete_message error is received" in {
      val response = Json.parse(
         """
           | {
           |   "ok" : false,
           |   "error": "cant_delete_message"
           | }
         """.stripMargin
      )

      an [CantDeleteMessageError] should be thrownBy (httpClient invokePrivate checkResponse(response))
   }

   "checkResponse()" should "raise an InvalidTsLatestError if the invalid_ts_latest error is received" in {
      val response = Json.parse(
         """
           | {
           |   "ok" : false,
           |   "error": "invalid_ts_latest"
           | }
         """.stripMargin
      )

      an [InvalidTsLatestError] should be thrownBy (httpClient invokePrivate checkResponse(response))
   }

   "checkResponse()" should "raise an InvalidTsOldest if the invalid_ts_oldest error is received" in {
      val response = Json.parse(
         """
           | {
           |   "ok" : false,
           |   "error": "invalid_ts_oldest"
           | }
         """.stripMargin
      )

      an [InvalidTsOldestError] should be thrownBy (httpClient invokePrivate checkResponse(response))
   }

   "checkResponse()" should "raise an UnknownSlackError if an unrecognized error is received" in {
      val response = Json.parse(
         """
           | {
           |   "ok" : false,
           |   "error": "unrecognized_error"
           | }
         """.stripMargin
      )

      an [UnknownSlackError] should be thrownBy (httpClient invokePrivate checkResponse(response))
   }

}
