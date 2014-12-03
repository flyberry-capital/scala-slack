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

import org.joda.time.DateTime
import play.api.libs.json.JsValue


/**
 * Class for representing a Slack message
 *
 * @param messageType Message type (usually just "message").
 * @param ts Message timestamp. Comes in the form of a UTC UNIX timestamp, and is used as a unique identifier (UID)
 *           for the message.
 * @param user The User ID of the user that sent the message.
 * @param text The text body of the message.
 * @param isStarred A boolean indicating whether this message has been starred (favorited).
 * @param attachments A list of JsValues (https://www.playframework.com/documentation/2.4.x/ScalaJson) representing JSON
 *                    objects containing attachments.
 * @param time The timestamp of the message converted into a Joda DateTime object.
 */
case class SlackMessage(messageType: String, ts: String, user: Option[String], text: Option[String], isStarred: Boolean,
                        attachments: List[JsValue], time: DateTime)
