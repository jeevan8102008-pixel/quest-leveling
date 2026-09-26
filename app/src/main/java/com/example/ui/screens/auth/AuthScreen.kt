package com.example.ui.screens.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.ui.components.GlowingDivider
import com.example.ui.components.LuminousCard
import com.example.ui.components.SoloButton
import com.example.ui.theme.DarkNavyBg
import com.example.ui.theme.DeepNavyCard
import com.example.ui.theme.ElectricBlue
import com.example.ui.theme.ElectricBlueDark
import com.example.ui.theme.ElectricBlueNeon
import com.example.ui.theme.ErrorRed
import com.example.ui.theme.LuminousDivider
import com.example.ui.theme.SuccessGreen
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextWhite

@Composable
fun AuthScreen(
  errorMessage: String?,
  onSignIn: (email: String, pass: String) -> Unit,
  onRegister: (email: String, pass: String, name: String) -> Unit,
  onClearError: () -> Unit
) {
  var isRegistering by remember { mutableStateOf(false) }
  var email by remember { mutableStateOf("") }
  var password by remember { mutableStateOf("") }
  var playerName by remember { mutableStateOf("") }
  var passwordVisible by remember { mutableStateOf(false) }
  var showHostingInfo by remember { mutableStateOf(false) }
  var copiedPublicUrl by remember { mutableStateOf(false) }

  val clipboardManager = LocalClipboardManager.current
  val publicAppUrl = "https://ais-pre-zashgh3lef56z325fp3mux-198687501467.asia-southeast1.run.app"

  Box(
    modifier = Modifier
      .fillMaxSize()
      .background(DarkNavyBg),
    contentAlignment = Alignment.Center
  ) {
    Column(
      modifier = Modifier
        .fillMaxSize()
        .verticalScroll(rememberScrollState())
        .padding(horizontal = 24.dp, vertical = 32.dp),
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.Center
    ) {
      Box(
        modifier = Modifier
          .widthIn(max = 480.dp)
          .fillMaxWidth(),
        contentAlignment = Alignment.Center
      ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {

          // App Emblem / Avatar Hero
          Box(
            modifier = Modifier
              .size(80.dp)
              .clip(CircleShape)
              .border(2.dp, ElectricBlueNeon, CircleShape)
              .background(DeepNavyCard),
            contentAlignment = Alignment.Center
          ) {
            Image(
              painter = painterResource(id = R.drawable.ic_quest_launcher),
              contentDescription = "Quest Emblem",
              modifier = Modifier.fillMaxSize(),
              contentScale = ContentScale.Crop
            )
          }

          Spacer(modifier = Modifier.height(16.dp))

          Text(
            text = "SOLO FITNESS SYSTEM",
            style = MaterialTheme.typography.displayMedium,
            fontWeight = FontWeight.Black,
            color = TextWhite,
            letterSpacing = 2.sp,
            textAlign = TextAlign.Center
          )

          Text(
            text = "Awaken Your Potential • E-Rank Initiation",
            style = MaterialTheme.typography.bodyMedium,
            color = ElectricBlueNeon,
            letterSpacing = 1.sp,
            modifier = Modifier.padding(top = 4.dp, bottom = 20.dp)
          )

          GlowingDivider(modifier = Modifier.padding(bottom = 20.dp))

          // Auth Form Card
          LuminousCard(
            modifier = Modifier.fillMaxWidth(),
            borderColor = ElectricBlueDark
          ) {
            Column(
              modifier = Modifier.padding(20.dp),
              horizontalAlignment = Alignment.CenterHorizontally
            ) {
              // Tab Switcher
              Row(
                modifier = Modifier
                  .fillMaxWidth()
                  .background(DarkNavyBg, RoundedCornerShape(8.dp))
                  .padding(4.dp),
                horizontalArrangement = Arrangement.SpaceBetween
              ) {
                Box(
                  modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(6.dp))
                    .background(if (!isRegistering) ElectricBlue else Color.Transparent)
                    .clickable {
                      isRegistering = false
                      onClearError()
                    }
                    .padding(vertical = 10.dp),
                  contentAlignment = Alignment.Center
                ) {
                  Text(
                    text = "SIGN IN",
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp,
                    color = if (!isRegistering) DarkNavyBg else TextMuted
                  )
                }

                Box(
                  modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(6.dp))
                    .background(if (isRegistering) ElectricBlue else Color.Transparent)
                    .clickable {
                      isRegistering = true
                      onClearError()
                    }
                    .padding(vertical = 10.dp),
                  contentAlignment = Alignment.Center
                ) {
                  Text(
                    text = "REGISTER",
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp,
                    color = if (isRegistering) DarkNavyBg else TextMuted
                  )
                }
              }

              Spacer(modifier = Modifier.height(20.dp))

              if (errorMessage != null) {
                Text(
                  text = errorMessage,
                  color = ErrorRed,
                  style = MaterialTheme.typography.bodyMedium,
                  modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp),
                  textAlign = TextAlign.Center
                )
              }

              if (isRegistering) {
                OutlinedTextField(
                  value = playerName,
                  onValueChange = { playerName = it },
                  label = { Text("Player Name") },
                  leadingIcon = {
                    Icon(Icons.Default.Person, contentDescription = "Player Name", tint = ElectricBlueNeon)
                  },
                  modifier = Modifier
                    .fillMaxWidth()
                    .testTag("auth_name_input"),
                  colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = ElectricBlueNeon,
                    unfocusedBorderColor = LuminousDivider,
                    focusedLabelColor = ElectricBlueNeon,
                    unfocusedLabelColor = TextMuted,
                    focusedTextColor = TextWhite,
                    unfocusedTextColor = TextWhite
                  ),
                  singleLine = true
                )
                Spacer(modifier = Modifier.height(12.dp))
              }

              OutlinedTextField(
                value = email,
                onValueChange = {
                  email = it
                  onClearError()
                },
                label = { Text("Player Email / Hunter ID") },
                leadingIcon = {
                  Icon(Icons.Default.Person, contentDescription = "Email", tint = ElectricBlueNeon)
                },
                modifier = Modifier
                  .fillMaxWidth()
                  .testTag("auth_email_input"),
                keyboardOptions = KeyboardOptions(
                  keyboardType = KeyboardType.Email,
                  imeAction = ImeAction.Next
                ),
                colors = OutlinedTextFieldDefaults.colors(
                  focusedBorderColor = ElectricBlueNeon,
                  unfocusedBorderColor = LuminousDivider,
                  focusedLabelColor = ElectricBlueNeon,
                  unfocusedLabelColor = TextMuted,
                  focusedTextColor = TextWhite,
                  unfocusedTextColor = TextWhite
                ),
                singleLine = true
              )

              Spacer(modifier = Modifier.height(12.dp))

              OutlinedTextField(
                value = password,
                onValueChange = {
                  password = it
                  onClearError()
                },
                label = { Text("Hunter Password") },
                leadingIcon = {
                  Icon(Icons.Default.Lock, contentDescription = "Password", tint = ElectricBlueNeon)
                },
                trailingIcon = {
                  IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(
                      imageVector = if (passwordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                      contentDescription = "Toggle Password Visibility",
                      tint = TextMuted
                    )
                  }
                },
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                modifier = Modifier
                  .fillMaxWidth()
                  .testTag("auth_password_input"),
                keyboardOptions = KeyboardOptions(
                  keyboardType = KeyboardType.Password,
                  imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(onDone = {
                  if (isRegistering) onRegister(email, password, playerName)
                  else onSignIn(email, password)
                }),
                colors = OutlinedTextFieldDefaults.colors(
                  focusedBorderColor = ElectricBlueNeon,
                  unfocusedBorderColor = LuminousDivider,
                  focusedLabelColor = ElectricBlueNeon,
                  unfocusedLabelColor = TextMuted,
                  focusedTextColor = TextWhite,
                  unfocusedTextColor = TextWhite
                ),
                singleLine = true
              )

              Spacer(modifier = Modifier.height(20.dp))

              SoloButton(
                text = if (isRegistering) "Awaken as Player" else "Enter System",
                onClick = {
                  if (isRegistering) {
                    onRegister(email, password, playerName)
                  } else {
                    onSignIn(email, password)
                  }
                },
                modifier = Modifier.fillMaxWidth(),
                testTag = "auth_submit_button"
              )

              Spacer(modifier = Modifier.height(14.dp))

              // Quick demo player login button for testing
              SoloButton(
                text = "Instant Demo Player (Sung Jin-Woo)",
                onClick = {
                  onRegister("hunter1@sololevel.com", "hunter123", "Sung Jin-Woo")
                },
                modifier = Modifier.fillMaxWidth(),
                isSecondary = true,
                testTag = "demo_player_button"
              )
            }
          }

          Spacer(modifier = Modifier.height(24.dp))

          // Shareable URL & Hosting Information
          LuminousCard(
            modifier = Modifier.fillMaxWidth(),
            backgroundColor = DarkNavyBg,
            borderColor = LuminousDivider
          ) {
            Column(modifier = Modifier.padding(16.dp)) {
              Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
              ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                  Icon(
                    imageVector = Icons.Default.Share,
                    contentDescription = "Share",
                    tint = ElectricBlueNeon,
                    modifier = Modifier.size(18.dp)
                  )
                  Spacer(modifier = Modifier.width(8.dp))
                  Text(
                    text = "PUBLIC SHAREABLE APP URL",
                    style = MaterialTheme.typography.labelSmall,
                    color = ElectricBlueNeon,
                    fontWeight = FontWeight.Bold
                  )
                }

                Text(
                  text = if (copiedPublicUrl) "COPIED!" else "COPY",
                  color = if (copiedPublicUrl) SuccessGreen else ElectricBlue,
                  fontWeight = FontWeight.Bold,
                  fontSize = 11.sp,
                  modifier = Modifier
                    .clip(RoundedCornerShape(4.dp))
                    .clickable {
                      clipboardManager.setText(AnnotatedString(publicAppUrl))
                      copiedPublicUrl = true
                    }
                    .padding(horizontal = 8.dp, vertical = 4.dp)
                )
              }

              Spacer(modifier = Modifier.height(6.dp))

              Text(
                text = publicAppUrl,
                style = MaterialTheme.typography.bodySmall,
                color = TextMuted,
                fontSize = 11.sp
              )

              Spacer(modifier = Modifier.height(10.dp))

              Row(
                modifier = Modifier
                  .clickable { showHostingInfo = !showHostingInfo }
                  .padding(vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
              ) {
                Icon(
                  imageVector = Icons.Default.Info,
                  contentDescription = "Info",
                  tint = TextMuted,
                  modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                  text = if (showHostingInfo) "Hide Hosting & Account Storage Guide" else "View Hosting & Account Storage Guide",
                  color = TextMuted,
                  fontSize = 11.sp
                )
              }

              if (showHostingInfo) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                  text = "• Account Isolation: Each player's profile, preferences, and quest logs are stored privately in encrypted Room local database tables isolated by player ID.\n• Secrets & API Keys: Managed server-side via AI Studio Secrets Panel and BuildConfig; zero secret keys in client assets.\n• Public Access: The shareable URL above is globally available for anyone to register their own hunter profile.",
                  style = MaterialTheme.typography.bodySmall,
                  color = TextWhite.copy(alpha = 0.8f),
                  fontSize = 11.sp,
                  lineHeight = 16.sp
                )
              }
            }
          }
        }
      }
    }
  }
}
