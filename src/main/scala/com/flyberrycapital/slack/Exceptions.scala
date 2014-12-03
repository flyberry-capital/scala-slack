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


/**
 * Exceptions for errors returned by the web API
 */
object Exceptions {
   class SlackError extends Exception
   class InvalidAuthError extends SlackError
   class NotAuthedError extends SlackError
   class AccountInactiveError extends SlackError
   class ChannelNotFoundError extends SlackError
   class ChannelArchivedError extends SlackError
   class NotInChannelError extends SlackError
   class RateLimitedError extends SlackError
   class NoMessageTextProvidedError extends SlackError
   class MessageTooLongError extends SlackError
   class MessageNotFoundError extends SlackError
   class EditWindowClosedError extends SlackError
   class CantUpdateMessageError extends SlackError
   class CantDeleteMessageError extends SlackError
   class InvalidTsLatestError extends SlackError
   class InvalidTsOldestError extends SlackError
   class UnknownSlackError(msg: String) extends Exception(msg)
}
