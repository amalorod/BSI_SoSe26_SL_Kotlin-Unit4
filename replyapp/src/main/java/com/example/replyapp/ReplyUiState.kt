package com.example.replyapp

import com.example.replyapp.data.Email
import com.example.replyapp.data.MailboxType
import com.example.replyapp.data.local.LocalEmailsDataProvider

data class ReplyUiState(
    val mailboxes: Map<MailboxType, List<Email>> = emptyMap(),
    val currentMailbox: MailboxType = MailboxType.Inbox,
    val currentSelectedEmail: Email = LocalEmailsDataProvider.defaultEmail,
    val isShowingHomepage: Boolean = true
) {
    val currentMailboxEmails: List<Email> = mailboxes[currentMailbox] ?: emptyList()
}