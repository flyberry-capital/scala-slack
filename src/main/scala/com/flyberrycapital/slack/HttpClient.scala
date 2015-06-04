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

package com.flyberrycapital.slack

import play.api.libs.json.{Json, JsValue}

import scalaj.http.{Http, HttpOptions}


/**
 * A simple HTTP client for querying the Slack web API and parsing responses.
 *
 * @param BaseURL The base URL for API calls.
 */
class HttpClient(BaseURL: String = "https://slack.com/api") {

   import Exceptions._

   private var connTimeoutMs: Int = 100
   private var readTimeoutMs: Int = 500

   @inline private def buildURL(method: String) = s"$BaseURL/$method"

   private def checkResponse(responseJSON: JsValue) {

      if ((responseJSON \ "ok").asOpt[Boolean] == Some(true))
         return

      (responseJSON \ "error").asOpt[String] match {
         case Some("invalid_auth") =>        throw new InvalidAuthError
         case Some("not_authed") =>          throw new NotAuthedError
         case Some("account_inactive") =>    throw new AccountInactiveError
         case Some("channel_not_found") =>   throw new ChannelNotFoundError
         case Some("is_archived") =>         throw new ChannelArchivedError
         case Some("not_in_channel") =>      throw new NotInChannelError
         case Some("rate_limited") =>        throw new RateLimitedError
         case Some("no_text") =>             throw new NoMessageTextProvidedError
         case Some("msg_too_long") =>        throw new MessageTooLongError
         case Some("message_not_found") =>   throw new MessageNotFoundError
         case Some("edit_window_closed") =>  throw new EditWindowClosedError
         case Some("cant_update_message") => throw new CantUpdateMessageError
         case Some("cant_delete_message") => throw new CantDeleteMessageError
         case Some("invalid_ts_latest") =>   throw new InvalidTsLatestError
         case Some("invalid_ts_oldest") =>   throw new InvalidTsOldestError
         case Some(x) =>                     throw new UnknownSlackError(x)
         case None =>
            throw new UnknownSlackError(s"Invalid response from Slack server: $responseJSON")
      }
   }

   def get(method: String, params: Map[String, String]): JsValue = {
      val result = Json.parse(Http(buildURL(method))
         .option(HttpOptions.connTimeout(connTimeoutMs))
         .option(HttpOptions.readTimeout(readTimeoutMs))
         .params(params)
         .asString)

      checkResponse(result)

      result
   }

   def post(method: String, data: Map[String, String]): JsValue = {
      val result = Json.parse(Http.post(buildURL(method))
         .option(HttpOptions.connTimeout(connTimeoutMs))
         .option(HttpOptions.readTimeout(readTimeoutMs))
         .params(data)
         .asString)

      checkResponse(result)

      result
   }

   def connTimeout(ms: Int): HttpClient = {
      connTimeoutMs = ms

      this
   }

   def readTimeout(ms: Int): HttpClient = {
      readTimeoutMs = ms

      this
   }
}
