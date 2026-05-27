package com.example.replyapp

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.replyapp.data.Email
import com.example.replyapp.data.MailboxType

@Composable
fun ReplyDetailsScreen(
    replyUiState: ReplyUiState,
    modifier: Modifier = Modifier,
    onBackPressed: () -> Unit = {},
) {
    BackHandler {
        onBackPressed()
    }
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.inverseOnSurface)
            .padding(top = dimensionResource(R.dimen.detail_card_list_item_padding_top))
    ) {
        ReplyDetailsScreenTopBar(
            onBackPressed = onBackPressed,
            replyUiState = replyUiState,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = dimensionResource(R.dimen.detail_topbar_padding_bottom))
        )
        ReplyEmailDetailsCard(
            email = replyUiState.currentSelectedEmail,
            mailboxType = replyUiState.currentMailbox,
            modifier = Modifier.padding(horizontal = dimensionResource(R.dimen.detail_card_outer_padding))
        )
    }
}

@Composable
private fun ReplyDetailsScreenTopBar(
    onBackPressed: () -> Unit,
    replyUiState: ReplyUiState,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        IconButton(
            onClick = onBackPressed,
            modifier = Modifier
            .padding(
                    start = dimensionResource(R.dimen.detail_topbar_back_button_padding_horizontal),
                    end = dimensionResource(R.dimen.detail_topbar_back_button_padding_horizontal),
                    top = dimensionResource(R.dimen.detail_topbar_back_button_padding_horizontal) + 16.dp, // Hier den Abstand vergrößern
            bottom = dimensionResource(R.dimen.detail_topbar_back_button_padding_horizontal),
        )
            .background(MaterialTheme.colorScheme.surface, shape = CircleShape),
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = stringResource(id = R.string.navigation_back)
            )
        }
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = dimensionResource(R.dimen.detail_topbar_back_button_padding_horizontal))
        ) {
            Text(
                text = stringResource(replyUiState.currentSelectedEmail.subject),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun ReplyEmailDetailsCard(
    email: Email,
    mailboxType: MailboxType,
    modifier: Modifier = Modifier,
    isFullScreen: Boolean = false
) {
    val context = LocalContext.current
    val displayToast = { text: String ->
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        shape = MaterialTheme.shapes.medium
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(R.dimen.detail_card_inner_padding))
        ) {
            DetailsScreenHeader(
                email,
                Modifier.fillMaxWidth()
            )
            if (isFullScreen) {
                Spacer(modifier = Modifier.size(dimensionResource(R.dimen.detail_content_padding_top)))
            } else {
                Text(
                    text = stringResource(email.subject),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.outline,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            top = dimensionResource(R.dimen.detail_content_padding_top),
                            bottom = dimensionResource(R.dimen.detail_expanded_subject_body_spacing)
                        )
                )
            }
            Text(
                text = stringResource(email.body),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            DetailsScreenButtonBar(mailboxType, displayToast)
        }
    }
}

@Composable
private fun DetailsScreenButtonBar(
    mailboxType: MailboxType,
    displayToast: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        when (mailboxType) {
            MailboxType.Drafts -> {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = dimensionResource(R.dimen.detail_button_bar_padding_vertical)),
                    horizontalArrangement = Arrangement.spacedBy(
                        dimensionResource(R.dimen.detail_button_bar_item_spacing)
                    )
                ) {
                    Button(
                        onClick = { displayToast("Continuing Draft") },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiaryContainer)
                    ) {
                        Text(
                            text = stringResource(id = R.string.continue_drafting),
                            color = MaterialTheme.colorScheme.onTertiaryContainer
                        )
                    }
                    Button(
                        onClick = { displayToast("Discarding Draft") },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.errorContainer)
                    ) {
                        Text(
                            text = stringResource(id = R.string.discard),
                            color = MaterialTheme.colorScheme.onErrorContainer
                        )
                    }
                }
            }

            MailboxType.Spam -> {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = dimensionResource(R.dimen.detail_button_bar_padding_vertical)),
                    horizontalArrangement = Arrangement.spacedBy(
                        dimensionResource(R.dimen.detail_button_bar_item_spacing)
                    )
                ) {
                    Button(
                        onClick = { displayToast("Moving to Inbox") },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
                    ) {
                        Text(
                            text = stringResource(id = R.string.move_to_inbox),
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                    Button(
                        onClick = { displayToast("Deleting Forever") },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.errorContainer)
                    ) {
                        Text(
                            text = stringResource(id = R.string.delete),
                            color = MaterialTheme.colorScheme.onErrorContainer
                        )
                    }
                }
            }

            else -> {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = dimensionResource(R.dimen.detail_button_bar_padding_vertical)),
                    horizontalArrangement = Arrangement.spacedBy(
                        dimensionResource(R.dimen.detail_button_bar_item_spacing)
                    )
                ) {
                    Button(
                        onClick = { displayToast("Replying") },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
                    ) {
                        Text(
                            text = stringResource(id = R.string.reply),
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                    Button(
                        onClick = { displayToast("Replying All") },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
                    ) {
                        Text(
                            text = stringResource(id = R.string.reply_all),
                            color = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun DetailsScreenHeader(email: Email, modifier: Modifier = Modifier) {
    Row(modifier = modifier) {
        ReplyProfileImage(
            drawableResource = email.sender.avatar,
            description = stringResource(email.sender.firstName) + " "
                    + stringResource(email.sender.lastName),
            modifier = Modifier.size(dimensionResource(R.dimen.email_header_profile_size))
        )
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(
                    horizontal = dimensionResource(R.dimen.email_header_content_padding_horizontal),
                    vertical = dimensionResource(R.dimen.email_header_content_padding_vertical)
                ),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(email.sender.firstName),
                style = MaterialTheme.typography.labelMedium
            )
            Text(
                text = stringResource(email.createdAt),
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.outline
            )
        }
    }
}