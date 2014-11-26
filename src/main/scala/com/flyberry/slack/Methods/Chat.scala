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

package com.flyberry.slack.Methods

import com.flyberry.slack.HttpClient
import org.joda.time.DateTime


/**
 * The container for Slack's 'chat' methods (https://api.slack.com/methods).
 */
class Chat(httpClient: HttpClient, apiToken: String) {

   import com.flyberry.slack.Responses._

   /**
    * https://api.slack.com/methods/chat.delete
    *
    * @param channel The channel ID of the message to be deleted.
    * @param ts The timestamp of the message to be deleted.
    * @return PostMessageResponse object.
    */
   def delete(channel: String, ts: String): PostMessageResponse = {
      val params = Map("channel" -> channel, "ts" -> ts.toString, "token" -> apiToken)

      val responseDict = httpClient.post("chat.delete", params)

      PostMessageResponse(
         (responseDict \ "ok").as[Boolean],
         (responseDict \ "ts").as[String],
         (responseDict \ "channel").as[String],
         new DateTime(((responseDict \ "ts").as[String].toDouble * 1000).toLong)
      )
   }

   /**
    * https://api.slack.com/methods/chat.delete
    *
    * @param postMessageResponse A PostMessageResponse object generated from a call to postMessage() or update().
    * @return PostMessageResponse object.
    */
   def delete(postMessageResponse: PostMessageResponse): PostMessageResponse = {
      delete(postMessageResponse.channel, postMessageResponse.ts)
   }

   /**
    * https://api.slack.com/methods/chat.postMessage
    *
    * @param channel The channel ID of the channel to send the message to.
    * @param text The text of the message to send.
    * @param params Optional additional params.
    * @return PostMessageResponse object.
    */
   def postMessage(channel: String, text: String,
                   params: Map[String, String] = Map()): PostMessageResponse = {

      val cleanedParams = params ++
         Map("channel" -> channel, "text" -> text, "token" -> apiToken)

      val responseDict = httpClient.post("chat.postMessage", cleanedParams)

      PostMessageResponse(
         (responseDict \ "ok").as[Boolean],
         (responseDict \ "ts").as[String],
         (responseDict \ "channel").as[String],
         new DateTime(((responseDict \ "ts").as[String].toDouble * 1000).toLong)
      )
   }

   /**
    * https://api.slack.com/methods/chat.update
    *
    * @param channel The channel ID of the message to be updated.
    * @param ts The timestamp of the message to be updated.
    * @param text The updated text for the message.
    * @return PostMessageResponse object.
    */
   def update(channel: String, ts: String, text: String): PostMessageResponse = {
      val params = Map("channel" -> channel, "ts" -> ts.toString, "token" -> apiToken, "text" -> text)

      val responseDict = httpClient.post("chat.update", params)

      PostMessageResponse(
         (responseDict \ "ok").as[Boolean],
         (responseDict \ "ts").as[String],
         (responseDict \ "channel").as[String],
         new DateTime(((responseDict \ "ts").as[String].toDouble * 1000).toLong)
      )
   }

   /**
    * https://api.slack.com/methods/chat.update
    *
    * @param postMessageResponse A PostMessageResponse object generated from a call to postMessage() or update().
    * @param text The updated text for the message.
    * @return PostMessageResponse object.
    */
   def update(postMessageResponse: PostMessageResponse, text: String): PostMessageResponse = {
      update(postMessageResponse.channel, postMessageResponse.ts, text)
   }

}