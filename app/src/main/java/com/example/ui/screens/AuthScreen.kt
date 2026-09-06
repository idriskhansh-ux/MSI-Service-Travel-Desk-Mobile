package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.model.DevicePlatform
import com.example.ui.theme.*

@Composable
fun AuthScreen(
  onLoginSuccess: (String) -> Unit,
  onShowNotice: (String) -> Unit
) {
  var email by remember { mutableStateOf("idrish.khan@msisurfaces.com") }
  var password by remember { mutableStateOf("MSI-Enterprise@2024") }
  var passwordVisible by remember { mutableStateOf(false) }
  var keepSessionActive by remember { mutableStateOf(true) }
  var selectedPlatform by remember { mutableStateOf(DevicePlatform.IOS) }

  val scrollState = rememberScrollState()

  Column(
    modifier = Modifier
      .fillMaxSize()
      .background(MaterialTheme.colorScheme.background)
      .verticalScroll(scrollState)
      .statusBarsPadding()
      .navigationBarsPadding()
      .padding(horizontal = 16.dp, vertical = 20.dp),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.spacedBy(16.dp)
  ) {
    // Ambient Header Banner Card
    Card(
      shape = RoundedCornerShape(16.dp),
      colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerLow),
      modifier = Modifier.fillMaxWidth()
    ) {
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .background(
            Brush.verticalGradient(
              listOf(
                PrimaryFixed.copy(alpha = 0.25f),
                MaterialTheme.colorScheme.surfaceContainerLow
              )
            )
          )
          .padding(vertical = 20.dp, horizontal = 16.dp),
        contentAlignment = Alignment.Center
      ) {
        Column(
          horizontalAlignment = Alignment.CenterHorizontally,
          verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          // MSI Services Corporate Logo Card
          Surface(
            shape = RoundedCornerShape(10.dp),
            color = MaterialTheme.colorScheme.surfaceContainerLowest,
            shadowElevation = 2.dp,
            modifier = Modifier
              .width(180.dp)
              .height(60.dp)
          ) {
            Box(
              modifier = Modifier
                .fillMaxSize()
                .padding(6.dp),
              contentAlignment = Alignment.Center
            ) {
              AsyncImage(
                model = "https://lh3.googleusercontent.com/aida-public/AB6AXuBH2UKM5wIaq-DOBUynW-GY5ckHQR0Ci0-EFZX1ajrREpXmXJwMMu86xa_jv51v20IFYfA4MKjNdyptoY51IfJVzT8dT6seqBxZ-OWkYmPOM6z7BzuegYFFvBPZICVlC3YfEZaMngJNdPTvTxp-oe0c7gycZ-_uCtq-OoLPfDTUzi3juip82yvwa5W3ysrEY-u8yb5JcrrWuYWlX3n-l89tN9uo7IbwaIyTmiDxSao-M4KPzBX3--8GUUf8ucwreCDonA",
                contentDescription = "MSI Services - Making Dream Services Attainable",
                contentScale = ContentScale.Fit,
                modifier = Modifier.fillMaxSize()
              )
            }
          }

          // Corporate Travel Desk Badge
          Box(
            modifier = Modifier
              .clip(RoundedCornerShape(100.dp))
              .background(MaterialTheme.colorScheme.surfaceContainerHighest)
              .padding(horizontal = 10.dp, vertical = 4.dp)
          ) {
            Row(
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
              Box(
                modifier = Modifier
                  .size(6.dp)
                  .clip(CircleShape)
                  .background(MaterialTheme.colorScheme.primary)
              )
              Text(
                text = "CORPORATE TRAVEL DESK",
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                letterSpacing = 0.8.sp
              )
            }
          }

          Text(
            text = "Welcome Back",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
          )

          Text(
            text = "Sign in with your official @msisurfaces.com enterprise credentials",
            fontSize = 13.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
            modifier = Modifier.widthIn(max = 300.dp)
          )
        }
      }
    }

    // Authentication Form Card
    Card(
      shape = RoundedCornerShape(16.dp),
      colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerLowest),
      elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
      modifier = Modifier.fillMaxWidth()
    ) {
      Column(
        modifier = Modifier.padding(18.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
      ) {
        // Device Environment / OS Selector
        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Text(
              text = "SELECT DEVICE ENVIRONMENT / OS",
              fontSize = 10.sp,
              fontWeight = FontWeight.Bold,
              color = MaterialTheme.colorScheme.outline,
              letterSpacing = 0.6.sp
            )
            Row(
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.spacedBy(3.dp)
            ) {
              Icon(
                Icons.Filled.Verified,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(13.dp)
              )
              Text(
                text = "Mobile Optimized",
                fontSize = 11.sp,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.SemiBold
              )
            }
          }

          // Segmented Toggle: iPhone vs Android
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .clip(RoundedCornerShape(12.dp))
              .background(MaterialTheme.colorScheme.surfaceContainerHigh)
              .padding(4.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
          ) {
            // iOS Tab
            val isIos = selectedPlatform == DevicePlatform.IOS
            Button(
              onClick = { selectedPlatform = DevicePlatform.IOS },
              colors = ButtonDefaults.buttonColors(
                containerColor = if (isIos) MaterialTheme.colorScheme.primary else Color.Transparent,
                contentColor = if (isIos) Color.White else MaterialTheme.colorScheme.onSurfaceVariant
              ),
              shape = RoundedCornerShape(8.dp),
              elevation = if (isIos) ButtonDefaults.buttonElevation(defaultElevation = 2.dp) else ButtonDefaults.buttonElevation(0.dp),
              modifier = Modifier
                .weight(1f)
                .height(40.dp)
                .testTag("tab_ios")
            ) {
              Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
              ) {
                Icon(Icons.Filled.PhoneIphone, contentDescription = null, modifier = Modifier.size(16.dp))
                Text("iPhone (iOS)", fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
                if (isIos) {
                  Icon(Icons.Filled.CheckCircle, contentDescription = null, modifier = Modifier.size(14.dp))
                }
              }
            }

            // Android Tab
            val isAndroid = selectedPlatform == DevicePlatform.ANDROID
            Button(
              onClick = { selectedPlatform = DevicePlatform.ANDROID },
              colors = ButtonDefaults.buttonColors(
                containerColor = if (isAndroid) MaterialTheme.colorScheme.primary else Color.Transparent,
                contentColor = if (isAndroid) Color.White else MaterialTheme.colorScheme.onSurfaceVariant
              ),
              shape = RoundedCornerShape(8.dp),
              elevation = if (isAndroid) ButtonDefaults.buttonElevation(defaultElevation = 2.dp) else ButtonDefaults.buttonElevation(0.dp),
              modifier = Modifier
                .weight(1f)
                .height(40.dp)
                .testTag("tab_android")
            ) {
              Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
              ) {
                Icon(Icons.Filled.Android, contentDescription = null, modifier = Modifier.size(16.dp))
                Text("Android", fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
                if (isAndroid) {
                  Icon(Icons.Filled.CheckCircle, contentDescription = null, modifier = Modifier.size(14.dp))
                }
              }
            }
          }
        }

        // Email Field
        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            Text(
              text = "Official Work Email ID",
              fontSize = 12.sp,
              fontWeight = FontWeight.SemiBold,
              color = MaterialTheme.colorScheme.onSurface
            )
            Text(
              text = "MSI Surfaces Single Sign-On",
              fontSize = 11.sp,
              color = MaterialTheme.colorScheme.outline
            )
          }

          OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            leadingIcon = {
              Icon(
                Icons.Outlined.Mail,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.outline,
                modifier = Modifier.size(19.dp)
              )
            },
            placeholder = { Text("e.g. firstname.lastname@msisurfaces.com", fontSize = 13.sp) },
            singleLine = true,
            modifier = Modifier
              .fillMaxWidth()
              .testTag("email_input"),
            shape = RoundedCornerShape(10.dp),
            colors = OutlinedTextFieldDefaults.colors(
              focusedContainerColor = MaterialTheme.colorScheme.surfaceContainerLowest,
              unfocusedContainerColor = MaterialTheme.colorScheme.surfaceContainerLow,
              focusedBorderColor = MaterialTheme.colorScheme.primary,
              unfocusedBorderColor = Color.Transparent
            )
          )

          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
          ) {
            Icon(
              Icons.Filled.VerifiedUser,
              contentDescription = null,
              tint = MaterialTheme.colorScheme.primary,
              modifier = Modifier.size(12.dp)
            )
            Text(
              text = "Requires authenticated @msisurfaces.com network account",
              fontSize = 11.sp,
              color = MaterialTheme.colorScheme.primary
            )
          }
        }

        // Password Field
        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Text(
              text = "Corporate Password",
              fontSize = 12.sp,
              fontWeight = FontWeight.SemiBold,
              color = MaterialTheme.colorScheme.onSurface
            )
            Text(
              text = "Forgot PIN / Password?",
              fontSize = 11.sp,
              fontWeight = FontWeight.Medium,
              color = MaterialTheme.colorScheme.primary,
              modifier = Modifier.clickable {
                onShowNotice("Password reset request forwarded to MSI Corporate Security Desk.")
              }
            )
          }

          OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            leadingIcon = {
              Icon(
                Icons.Outlined.Lock,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.outline,
                modifier = Modifier.size(19.dp)
              )
            },
            trailingIcon = {
              IconButton(onClick = { passwordVisible = !passwordVisible }) {
                Icon(
                  if (passwordVisible) Icons.Filled.VisibilityOff else Icons.Filled.Visibility,
                  contentDescription = "Toggle password visibility",
                  tint = MaterialTheme.colorScheme.outline,
                  modifier = Modifier.size(20.dp)
                )
              }
            },
            placeholder = { Text("Enter secure password", fontSize = 13.sp) },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            singleLine = true,
            modifier = Modifier
              .fillMaxWidth()
              .testTag("password_input"),
            shape = RoundedCornerShape(10.dp),
            colors = OutlinedTextFieldDefaults.colors(
              focusedContainerColor = MaterialTheme.colorScheme.surfaceContainerLowest,
              unfocusedContainerColor = MaterialTheme.colorScheme.surfaceContainerLow,
              focusedBorderColor = MaterialTheme.colorScheme.primary,
              unfocusedBorderColor = Color.Transparent
            )
          )
        }

        // Keep dispatch session active checkbox
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.clickable { keepSessionActive = !keepSessionActive }
          ) {
            Checkbox(
              checked = keepSessionActive,
              onCheckedChange = { keepSessionActive = it },
              colors = CheckboxDefaults.colors(checkedColor = MaterialTheme.colorScheme.primary)
            )
            Text(
              text = "Keep dispatch session active",
              fontSize = 13.sp,
              color = MaterialTheme.colorScheme.onSurfaceVariant
            )
          }
          Text(
            text = "30-day token",
            fontSize = 11.sp,
            color = MaterialTheme.colorScheme.outline
          )
        }

        // Primary Submit Button
        Button(
          onClick = {
            if (email.endsWith("@msisurfaces.com")) {
              onLoginSuccess(email)
            } else {
              onShowNotice("Please provide a valid @msisurfaces.com enterprise email.")
            }
          },
          modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .testTag("authorize_button"),
          shape = RoundedCornerShape(10.dp),
          colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
        ) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
          ) {
            Text(
              text = "Authorize & Access Portal",
              fontWeight = FontWeight.Bold,
              fontSize = 14.sp
            )
            Icon(Icons.Default.ArrowForward, contentDescription = null, modifier = Modifier.size(18.dp))
          }
        }

        // Desk Administrator Dispatch Card
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(MaterialTheme.colorScheme.surfaceContainerLow)
            .padding(12.dp)
        ) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Row(
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
              Box(
                modifier = Modifier
                  .size(36.dp)
                  .clip(CircleShape)
                  .background(MaterialTheme.colorScheme.primary),
                contentAlignment = Alignment.Center
              ) {
                Text(
                  text = "IK",
                  color = Color.White,
                  fontWeight = FontWeight.Bold,
                  fontSize = 14.sp
                )
              }
              Column {
                Text(
                  text = "Idrish Khan",
                  fontWeight = FontWeight.Bold,
                  fontSize = 13.sp,
                  color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                  text = "Lead Dispatcher • Travel Desk Admin",
                  fontSize = 11.sp,
                  color = MaterialTheme.colorScheme.onSurfaceVariant
                )
              }
            }
            Box(
              modifier = Modifier
                .clip(RoundedCornerShape(100.dp))
                .background(MaterialTheme.colorScheme.surfaceContainerHighest)
                .padding(horizontal = 8.dp, vertical = 3.dp)
            ) {
              Text(
                text = "Active Desk",
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
              )
            }
          }
        }
      }
    }

    // Security Footnote & Support Links
    Column(
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.spacedBy(6.dp),
      modifier = Modifier.padding(horizontal = 12.dp)
    ) {
      Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
      ) {
        Icon(
          Icons.Filled.Verified,
          contentDescription = null,
          tint = MaterialTheme.colorScheme.primary,
          modifier = Modifier.size(14.dp)
        )
        Text(
          text = "Secured with 256-bit TLS • Enterprise Travel Policy Managed by MSI Global Desk",
          fontSize = 11.sp,
          color = MaterialTheme.colorScheme.onSurfaceVariant,
          textAlign = TextAlign.Center
        )
      }

      Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp)
      ) {
        Text(
          text = "Optimized for iPhone (Apple iOS) & Android devices",
          fontSize = 10.sp,
          color = MaterialTheme.colorScheme.outline
        )
        Text("•", color = MaterialTheme.colorScheme.outline, fontSize = 10.sp)
        Text(
          text = "Spend caps calibrated in USD ($)",
          fontSize = 10.sp,
          fontWeight = FontWeight.SemiBold,
          color = MaterialTheme.colorScheme.primary
        )
      }

      Spacer(modifier = Modifier.height(6.dp))

      Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
      ) {
        Text(
          text = "New Team Member?",
          fontSize = 12.sp,
          color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
          text = "Request Travel Desk Access",
          fontSize = 12.sp,
          fontWeight = FontWeight.Bold,
          color = MaterialTheme.colorScheme.primary,
          modifier = Modifier.clickable {
            onShowNotice("Access Request ticket queued for Travel Desk Admin approval.")
          }
        )
      }

      Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        TextButton(
          onClick = { onShowNotice("Dispatch helpline active 24/7 on Slack #travel-desk") },
          contentPadding = PaddingValues(0.dp)
        ) {
          Icon(Icons.Outlined.SupportAgent, contentDescription = null, modifier = Modifier.size(14.dp), tint = MaterialTheme.colorScheme.outline)
          Spacer(modifier = Modifier.width(3.dp))
          Text("Global Support", fontSize = 11.sp, color = MaterialTheme.colorScheme.outline)
        }
        Text("•", color = MaterialTheme.colorScheme.outline, fontSize = 11.sp)
        TextButton(
          onClick = { onShowNotice("MSI Global Travel Entitlement & Spending Caps in USD ($) - Policy v4.2") },
          contentPadding = PaddingValues(0.dp)
        ) {
          Icon(Icons.Outlined.Policy, contentDescription = null, modifier = Modifier.size(14.dp), tint = MaterialTheme.colorScheme.outline)
          Spacer(modifier = Modifier.width(3.dp))
          Text("Corporate Travel Policy (USD $)", fontSize = 11.sp, color = MaterialTheme.colorScheme.outline)
        }
      }
    }
  }
}
