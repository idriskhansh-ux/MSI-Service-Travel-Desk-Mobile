package com.example.ui.components

import androidx.compose.animation.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import com.example.model.UserProfileData
import com.example.ui.theme.*

@Composable
fun PortalTopAppBar(
  title: String = "Travel Desk",
  subtitle: String = "Dashboard",
  onNotificationsClick: () -> Unit = {},
  onProfileClick: () -> Unit = {}
) {
  Surface(
    modifier = Modifier.fillMaxWidth(),
    color = MaterialTheme.colorScheme.surface.copy(alpha = 0.95f),
    shadowElevation = 2.dp
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .statusBarsPadding()
        .height(64.dp)
        .padding(horizontal = 16.dp),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.SpaceBetween
    ) {
      // MSI Brand Monogram & Title
      Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier.weight(1f, fill = false)
      ) {
        Box(
          modifier = Modifier
            .size(38.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(MaterialTheme.colorScheme.surfaceContainerLowest)
            .border(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.4f), RoundedCornerShape(8.dp)),
          contentAlignment = Alignment.Center
        ) {
          Text(
            text = "MSI",
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.primary,
            letterSpacing = 0.5.sp
          )
        }

        Column(modifier = Modifier.weight(1f, fill = false)) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
          ) {
            Text(
              text = title,
              style = MaterialTheme.typography.titleMedium,
              fontWeight = FontWeight.Bold,
              color = MaterialTheme.colorScheme.onSurface,
              maxLines = 1,
              overflow = TextOverflow.Ellipsis
            )
            Box(
              modifier = Modifier
                .clip(RoundedCornerShape(100.dp))
                .background(PrimaryFixed)
                .padding(horizontal = 6.dp, vertical = 2.dp)
            ) {
              Text(
                text = "ADMIN",
                fontSize = 9.sp,
                fontWeight = FontWeight.ExtraBold,
                color = OnPrimaryFixed,
                letterSpacing = 0.6.sp
              )
            }
          }
          Text(
            text = subtitle,
            fontSize = 11.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
          )
        }
      }

      // Actions: Notification with Badge + Profile Avatar
      Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp)
      ) {
        Box {
          IconButton(
            onClick = onNotificationsClick,
            modifier = Modifier.size(40.dp)
          ) {
            Icon(
              imageVector = Icons.Outlined.Notifications,
              contentDescription = "Notifications",
              tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
          }
          Box(
            modifier = Modifier
              .align(Alignment.TopEnd)
              .padding(top = 8.dp, end = 8.dp)
              .size(8.dp)
              .clip(CircleShape)
              .background(MaterialTheme.colorScheme.error)
              .border(1.5.dp, MaterialTheme.colorScheme.surface, CircleShape)
          )
        }

        Box(
          modifier = Modifier
            .padding(start = 4.dp)
            .clickable(onClick = onProfileClick)
        ) {
          Box(
            modifier = Modifier
              .size(36.dp)
              .clip(CircleShape)
              .border(1.5.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.4f), CircleShape)
          ) {
            AsyncImage(
              model = "https://lh3.googleusercontent.com/aida-public/AB6AXuBBTaFhr0iO6CyjLH4VTj3t8HC_iP3bgAmN-KQ20YZGjQUVygu0Skjz8eZT15QVJikqDKWFc0lNg-iDsRIR2UTgTjgV3qDKisMieies3M48pNyGsRAdA7l8r43Xm0C7mbcLHJcQoFFdkL-SqXXRYvK4d9f_vucdnBqyMIYRv5PykLlQ0FAoHj8T55i24BoF34_3naGJThhSIw1EjGSbmXk4D9ZvrfM4GvfScvMf--fi1drZ9vkzQv9uk4DxBtEJs5GkzA",
              contentDescription = "User Avatar",
              contentScale = ContentScale.Crop,
              modifier = Modifier.fillMaxSize()
            )
          }
          Box(
            modifier = Modifier
              .align(Alignment.BottomEnd)
              .size(10.dp)
              .clip(CircleShape)
              .background(EmeraldGreen)
              .border(1.5.dp, MaterialTheme.colorScheme.surface, CircleShape)
          )
        }
      }
    }
  }
}

enum class NavigationTab(val label: String, val testTag: String) {
  TRIPS("Trips", "tab_trips"),
  NEW_REQUEST("New Request", "tab_new_request"),
  POLICY_CAPS("Policy Caps", "tab_policy_caps"),
  PROFILE("Profile", "tab_profile")
}

@Composable
fun PortalBottomNavBar(
  currentTab: NavigationTab,
  onTabSelected: (NavigationTab) -> Unit
) {
  Surface(
    modifier = Modifier
      .fillMaxWidth()
      .windowInsetsPadding(WindowInsets.navigationBars),
    color = MaterialTheme.colorScheme.surfaceContainerLowest.copy(alpha = 0.98f),
    shadowElevation = 8.dp
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .height(64.dp)
        .padding(horizontal = 8.dp),
      horizontalArrangement = Arrangement.SpaceAround,
      verticalAlignment = Alignment.CenterVertically
    ) {
      NavigationTab.entries.forEach { tab ->
        val selected = tab == currentTab
        val activeColor = MaterialTheme.colorScheme.primary
        val inactiveColor = MaterialTheme.colorScheme.onSurfaceVariant

        Column(
          horizontalAlignment = Alignment.CenterHorizontally,
          verticalArrangement = Arrangement.Center,
          modifier = Modifier
            .weight(1f)
            .fillMaxHeight()
            .clip(RoundedCornerShape(8.dp))
            .clickable { onTabSelected(tab) }
            .testTag(tab.testTag)
        ) {
          Box {
            Icon(
              imageVector = when (tab) {
                NavigationTab.TRIPS -> if (selected) Icons.Filled.FlightTakeoff else Icons.Outlined.FlightTakeoff
                NavigationTab.NEW_REQUEST -> if (selected) Icons.Filled.AddCircle else Icons.Outlined.AddCircle
                NavigationTab.POLICY_CAPS -> if (selected) Icons.Filled.VerifiedUser else Icons.Outlined.VerifiedUser
                NavigationTab.PROFILE -> if (selected) Icons.Filled.Badge else Icons.Outlined.Badge
              },
              contentDescription = tab.label,
              tint = if (selected) activeColor else inactiveColor,
              modifier = Modifier.size(22.dp)
            )

            // Badge for New Request / Pending
            if (tab == NavigationTab.NEW_REQUEST) {
              Box(
                modifier = Modifier
                  .align(Alignment.TopEnd)
                  .offset(x = 10.dp, y = (-4).dp)
                  .size(16.dp)
                  .clip(CircleShape)
                  .background(PrimaryBronze),
                contentAlignment = Alignment.Center
              ) {
                Text(
                  text = "7",
                  fontSize = 9.sp,
                  fontWeight = FontWeight.Bold,
                  color = Color.White
                )
              }
            }
          }

          Spacer(modifier = Modifier.height(3.dp))

          Text(
            text = tab.label,
            fontSize = 11.sp,
            fontWeight = if (selected) FontWeight.Bold else FontWeight.Medium,
            color = if (selected) activeColor else inactiveColor
          )
        }
      }
    }
  }
}

@Composable
fun BoardingPassQrDialog(
  pnr: String = "DL-X8921K",
  flight: String = "Delta Flight DL 2940",
  passenger: String = "Rahul Sharma • Seat 14B",
  onDismiss: () -> Unit
) {
  Dialog(onDismissRequest = onDismiss) {
    Card(
      shape = RoundedCornerShape(20.dp),
      colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerLowest),
      modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)
    ) {
      Column(
        modifier = Modifier.padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
      ) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Column {
            Text(
              text = "BOARDING PASS PASSCODE",
              fontSize = 10.sp,
              fontWeight = FontWeight.Bold,
              color = MaterialTheme.colorScheme.primary,
              letterSpacing = 0.8.sp
            )
            Text(
              text = flight,
              style = MaterialTheme.typography.titleMedium,
              fontWeight = FontWeight.Bold
            )
            Text(
              text = passenger,
              fontSize = 13.sp,
              color = MaterialTheme.colorScheme.onSurfaceVariant
            )
          }
          IconButton(onClick = onDismiss) {
            Icon(Icons.Default.Close, contentDescription = "Close")
          }
        }

        // Realistic Simulated QR Code Matrix
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.surfaceContainerLow)
            .padding(16.dp),
          contentAlignment = Alignment.Center
        ) {
          Canvas(modifier = Modifier.size(140.dp)) {
            val step = size.width / 14f
            val dark = Color(0xFF191C1D)
            // Draw standard QR corner finder boxes
            drawRect(dark, topLeft = Offset(0f, 0f), size = Size(step * 4, step * 4))
            drawRect(Color.White, topLeft = Offset(step, step), size = Size(step * 2, step * 2))
            drawRect(dark, topLeft = Offset(step * 1.5f, step * 1.5f), size = Size(step, step))

            drawRect(dark, topLeft = Offset(size.width - step * 4, 0f), size = Size(step * 4, step * 4))
            drawRect(Color.White, topLeft = Offset(size.width - step * 3, step), size = Size(step * 2, step * 2))
            drawRect(dark, topLeft = Offset(size.width - step * 2.5f, step * 1.5f), size = Size(step, step))

            drawRect(dark, topLeft = Offset(0f, size.height - step * 4), size = Size(step * 4, step * 4))
            drawRect(Color.White, topLeft = Offset(step, size.height - step * 3), size = Size(step * 2, step * 2))
            drawRect(dark, topLeft = Offset(step * 1.5f, size.height - step * 2.5f), size = Size(step, step))

            // Random styled data blocks
            for (i in 0..13) {
              for (j in 0..13) {
                if ((i > 4 || j > 4) && (i < 9 || j > 4) && (i > 4 || j < 9)) {
                  if ((i * 7 + j * 13 + 5) % 3 == 0) {
                    drawRect(dark, topLeft = Offset(i * step, j * step), size = Size(step * 0.9f, step * 0.9f))
                  }
                }
              }
            }
          }
        }

        Text(
          text = "PNR: $pnr",
          fontSize = 15.sp,
          fontWeight = FontWeight.Bold,
          fontFamily = FontFamily.Monospace,
          color = MaterialTheme.colorScheme.onSurface
        )

        Text(
          text = "Gate closes 15 mins prior to departure • Validated by MSI Desk",
          fontSize = 11.sp,
          color = MaterialTheme.colorScheme.onSurfaceVariant,
          textAlign = TextAlign.Center
        )

        Button(
          onClick = onDismiss,
          modifier = Modifier.fillMaxWidth(),
          shape = RoundedCornerShape(10.dp),
          colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
        ) {
          Text("Done", fontWeight = FontWeight.Bold)
        }
      }
    }
  }
}

@Composable
fun HotelCheckInDialog(
  hotelName: String = "Chicago Marriott Magnificent Mile",
  confirmation: String = "MAR-9941203-CHI",
  onDismiss: () -> Unit
) {
  Dialog(onDismissRequest = onDismiss) {
    Card(
      shape = RoundedCornerShape(20.dp),
      colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerLowest),
      modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)
    ) {
      Column(
        modifier = Modifier.padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
      ) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Column {
            Text(
              text = "FAST CHECK-IN DESK",
              fontSize = 10.sp,
              fontWeight = FontWeight.Bold,
              color = MaterialTheme.colorScheme.primary,
              letterSpacing = 0.8.sp
            )
            Text(
              text = hotelName,
              style = MaterialTheme.typography.titleMedium,
              fontWeight = FontWeight.Bold
            )
            Text(
              text = "Confirmation: $confirmation",
              fontSize = 12.sp,
              color = MaterialTheme.colorScheme.onSurfaceVariant
            )
          }
          IconButton(onClick = onDismiss) {
            Icon(Icons.Default.Close, contentDescription = "Close")
          }
        }

        Box(
          modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.surfaceContainerLow)
            .padding(14.dp)
        ) {
          Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Row(
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
              Icon(
                Icons.Filled.PinDrop,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(20.dp)
              )
              Text(
                text = "Front Desk Expedited Corporate Line",
                fontWeight = FontWeight.Bold,
                fontSize = 13.sp
              )
            }
            Text(
              text = "Present your corporate employee badge & this digital reservation voucher. Room billing is routed directly to MSI Services Global Operations account.",
              fontSize = 12.sp,
              color = MaterialTheme.colorScheme.onSurfaceVariant,
              lineHeight = 17.sp
            )
          }
        }

        Button(
          onClick = onDismiss,
          modifier = Modifier.fillMaxWidth(),
          shape = RoundedCornerShape(10.dp),
          colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
        ) {
          Text("Close Instructions", fontWeight = FontWeight.Bold)
        }
      }
    }
  }
}

@Composable
fun TravelIdCardDialog(
  user: UserProfileData,
  onDismiss: () -> Unit
) {
  Dialog(onDismissRequest = onDismiss) {
    Card(
      shape = RoundedCornerShape(20.dp),
      colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerLowest),
      modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)
    ) {
      Column(
        modifier = Modifier.padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
      ) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
          ) {
            Icon(Icons.Filled.CreditCard, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
            Text("MSI Corporate Travel ID", fontWeight = FontWeight.Bold, fontSize = 16.sp)
          }
          IconButton(onClick = onDismiss) {
            Icon(Icons.Default.Close, contentDescription = "Close")
          }
        }

        // Executive ID Card Visual
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(
              androidx.compose.ui.graphics.Brush.linearGradient(
                listOf(Color(0xFF1E2124), Color(0xFF32363A), Color(0xFF735726))
              )
            )
            .padding(16.dp)
        ) {
          Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Text("MSI SERVICES", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 14.sp)
              Text("GLOBAL ELITE", color = PrimaryFixed, fontWeight = FontWeight.Bold, fontSize = 10.sp)
            }

            Row(
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
              Box(
                modifier = Modifier
                  .size(48.dp)
                  .clip(CircleShape)
                  .background(PrimaryFixed)
                  .border(2.dp, Color.White, CircleShape),
                contentAlignment = Alignment.Center
              ) {
                Text(
                  text = "IK",
                  fontWeight = FontWeight.Bold,
                  color = OnPrimaryFixed,
                  fontSize = 18.sp
                )
              }

              Column {
                Text(user.name, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Text(user.empId, color = Color.LightGray, fontSize = 12.sp, fontFamily = FontFamily.Monospace)
                Text(user.costCenter, color = PrimaryFixedDim, fontSize = 11.sp)
              }
            }

            Divider(color = Color.White.copy(alpha = 0.2f))

            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween
            ) {
              Column {
                Text("ENTITLEMENT", color = Color.Gray, fontSize = 9.sp, fontWeight = FontWeight.Bold)
                Text("Band A Executive", color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
              }
              Column(horizontalAlignment = Alignment.End) {
                Text("STATUS", color = Color.Gray, fontSize = 9.sp, fontWeight = FontWeight.Bold)
                Text("Root Admin", color = EmeraldGreen, fontSize = 11.sp, fontWeight = FontWeight.Bold)
              }
            }
          }
        }

        Text(
          text = "24/7 Corporate Concierge Hotline: +1 (800) 555-0199 • Group_MSI.TravelDesk@msisurfaces.com",
          fontSize = 11.sp,
          color = MaterialTheme.colorScheme.onSurfaceVariant,
          textAlign = TextAlign.Center
        )

        Button(
          onClick = onDismiss,
          modifier = Modifier.fillMaxWidth(),
          shape = RoundedCornerShape(10.dp),
          colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
        ) {
          Text("Done", fontWeight = FontWeight.Bold)
        }
      }
    }
  }
}

@Composable
fun FloatingToastBanner(
  message: String?,
  onDismiss: () -> Unit
) {
  AnimatedVisibility(
    visible = message != null,
    enter = slideInVertically(initialOffsetY = { -it }) + fadeIn(),
    exit = slideOutVertically(targetOffsetY = { -it }) + fadeOut(),
    modifier = Modifier
      .fillMaxWidth()
      .padding(horizontal = 16.dp, vertical = 12.dp)
  ) {
    Card(
      shape = RoundedCornerShape(12.dp),
      colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.inverseSurface),
      elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
      modifier = Modifier.fillMaxWidth()
    ) {
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
      ) {
        Row(
          modifier = Modifier.weight(1f),
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
          Icon(
            imageVector = Icons.Filled.TaskAlt,
            contentDescription = null,
            tint = PrimaryFixed,
            modifier = Modifier.size(22.dp)
          )
          Text(
            text = message ?: "",
            color = MaterialTheme.colorScheme.inverseOnSurface,
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium,
            lineHeight = 18.sp
          )
        }
        IconButton(
          onClick = onDismiss,
          modifier = Modifier.size(28.dp)
        ) {
          Icon(
            imageVector = Icons.Default.Close,
            contentDescription = "Dismiss",
            tint = MaterialTheme.colorScheme.inverseOnSurface.copy(alpha = 0.7f),
            modifier = Modifier.size(16.dp)
          )
        }
      }
    }
  }
}
