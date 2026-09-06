package com.example.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.data.TravelRepository
import com.example.ui.components.TravelIdCardDialog
import com.example.ui.theme.*

@Composable
fun ProfileScreen(
  repository: TravelRepository,
  onLogoutRequested: () -> Unit,
  modifier: Modifier = Modifier
) {
  val user by repository.userProfile.collectAsState()
  val isSyncing by repository.isWorkdaySyncing.collectAsState()

  var showIdCardModal by remember { mutableStateOf(false) }
  var showPreferencesEditModal by remember { mutableStateOf(false) }

  if (showIdCardModal) {
    TravelIdCardDialog(user = user, onDismiss = { showIdCardModal = false })
  }

  if (showPreferencesEditModal) {
    AlertDialog(
      onDismissRequest = { showPreferencesEditModal = false },
      title = { Text("Modify Travel & Loyalty Preferences", fontWeight = FontWeight.Bold, fontSize = 16.sp) },
      text = {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
          Text("Update frequent flyer identifiers and hotel loyalty tiers saved to your enterprise profile:", fontSize = 13.sp)
          OutlinedTextField(
            value = user.frequentFlyerAccounts.firstOrNull()?.second ?: "",
            onValueChange = {},
            label = { Text("IndiGo 6E Rewards") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
          )
          OutlinedTextField(
            value = user.hotelLoyalties.firstOrNull()?.second ?: "",
            onValueChange = {},
            label = { Text("Marriott Bonvoy #") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
          )
        }
      },
      confirmButton = {
        Button(
          onClick = {
            showPreferencesEditModal = false
            repository.showToast("Preferences updated and synced with HRMS.")
          },
          colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
        ) {
          Text("Save Changes")
        }
      },
      dismissButton = {
        TextButton(onClick = { showPreferencesEditModal = false }) {
          Text("Cancel")
        }
      }
    )
  }

  LazyColumn(
    modifier = modifier
      .fillMaxSize()
      .background(MaterialTheme.colorScheme.surface)
      .padding(horizontal = 16.dp),
    verticalArrangement = Arrangement.spacedBy(16.dp),
    contentPadding = PaddingValues(top = 16.dp, bottom = 28.dp)
  ) {
    // Executive Monogram / Profile Header Card
    item {
      Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerLowest),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(
          modifier = Modifier.padding(16.dp),
          verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(14.dp),
            verticalAlignment = Alignment.Top
          ) {
            // Profile Avatar with Verified Ring
            Box(modifier = Modifier.size(76.dp)) {
              Box(
                modifier = Modifier
                  .size(72.dp)
                  .clip(CircleShape)
                  .background(MaterialTheme.colorScheme.surfaceContainerHigh)
                  .border(2.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.5f), CircleShape)
              ) {
                AsyncImage(
                  model = "https://lh3.googleusercontent.com/aida-public/AB6AXuBBTaFhr0iO6CyjLH4VTj3t8HC_iP3bgAmN-KQ20YZGjQUVygu0Skjz8eZT15QVJikqDKWFc0lNg-iDsRIR2UTgTjgV3qDKisMieies3M48pNyGsRAdA7l8r43Xm0C7mbcLHJcQoFFdkL-SqXXRYvK4d9f_vucdnBqyMIYRv5PykLlQ0FAoHj8T55i24BoF34_3naGJThhSIw1EjGSbmXk4D9ZvrfM4GvfScvMf--fi1drZ9vkzQv9uk4DxBtEJs5GkzA",
                  contentDescription = user.name,
                  contentScale = ContentScale.Crop,
                  modifier = Modifier.fillMaxSize()
                )
              }
              Box(
                modifier = Modifier
                  .align(Alignment.BottomEnd)
                  .size(18.dp)
                  .clip(CircleShape)
                  .background(EmeraldGreen),
                contentAlignment = Alignment.Center
              ) {
                Icon(Icons.Filled.Check, contentDescription = "Verified", tint = Color.White, modifier = Modifier.size(12.dp))
              }
            }

            // Identity & Corporate Designation
            Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(2.dp)) {
              Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
              ) {
                Row(
                  verticalAlignment = Alignment.CenterVertically,
                  horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                  Text(user.name, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                  Icon(Icons.Filled.Verified, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(16.dp))
                }
                Box(
                  modifier = Modifier
                    .clip(RoundedCornerShape(100.dp))
                    .background(PrimaryFixed)
                    .padding(horizontal = 8.dp, vertical = 2.dp)
                ) {
                  Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(3.dp)) {
                    Icon(Icons.Filled.WorkspacePremium, contentDescription = null, tint = OnPrimaryFixed, modifier = Modifier.size(11.dp))
                    Text("Super Admin", fontSize = 9.sp, fontWeight = FontWeight.ExtraBold, color = OnPrimaryFixed)
                  }
                }
              }

              Text(user.role, fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.primary)
              Text(user.department, fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)

              Row(
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                modifier = Modifier.padding(top = 4.dp)
              ) {
                Box(
                  modifier = Modifier
                    .clip(RoundedCornerShape(4.dp))
                    .background(MaterialTheme.colorScheme.surfaceContainer)
                    .padding(horizontal = 6.dp, vertical = 2.dp)
                ) {
                  Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(3.dp)) {
                    Icon(Icons.Outlined.Badge, contentDescription = null, modifier = Modifier.size(11.dp), tint = MaterialTheme.colorScheme.outline)
                    Text("ID: #MSI-8841", fontSize = 10.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace)
                  }
                }
                Box(
                  modifier = Modifier
                    .clip(RoundedCornerShape(4.dp))
                    .background(MaterialTheme.colorScheme.surfaceContainer)
                    .padding(horizontal = 6.dp, vertical = 2.dp)
                ) {
                  Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(3.dp)) {
                    Icon(Icons.Outlined.Domain, contentDescription = null, modifier = Modifier.size(11.dp), tint = MaterialTheme.colorScheme.outline)
                    Text("CC-4010 Ent. Arch", fontSize = 10.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace)
                  }
                }
              }
            }
          }

          // Contact Info Pills
          Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Box(
              modifier = Modifier
                .weight(1f)
                .clip(RoundedCornerShape(8.dp))
                .background(MaterialTheme.colorScheme.surfaceContainerLow)
                .padding(8.dp)
            ) {
              Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                Icon(Icons.Filled.Mail, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(14.dp))
                Text(user.email, fontSize = 11.sp, maxLines = 1)
              }
            }

            Box(
              modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .background(MaterialTheme.colorScheme.surfaceContainerLow)
                .padding(8.dp)
            ) {
              Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                Icon(Icons.Filled.Call, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(14.dp))
                Text(user.phone, fontSize = 11.sp, fontFamily = FontFamily.Monospace)
              }
            }
          }

          // Operational Stats Trio
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
          ) {
            Box(
              modifier = Modifier
                .weight(1f)
                .clip(RoundedCornerShape(10.dp))
                .background(MaterialTheme.colorScheme.surfaceContainerLow)
                .padding(10.dp),
              contentAlignment = Alignment.Center
            ) {
              Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("TRIPS TAKEN", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.outline)
                Text("${user.tripsTaken}", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
                Text(user.tripsTakenDetail, fontSize = 10.sp, color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Medium)
              }
            }

            Box(
              modifier = Modifier
                .weight(1f)
                .clip(RoundedCornerShape(10.dp))
                .background(MaterialTheme.colorScheme.surfaceContainerLow)
                .padding(10.dp),
              contentAlignment = Alignment.Center
            ) {
              Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("ACTIVE BOOKINGS", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.outline)
                Text("${user.activeBookings}", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                Text(user.activeBookingsDetail, fontSize = 10.sp, color = AmberNotice, fontWeight = FontWeight.Medium)
              }
            }

            Box(
              modifier = Modifier
                .weight(1f)
                .clip(RoundedCornerShape(10.dp))
                .background(MaterialTheme.colorScheme.surfaceContainerLow)
                .padding(10.dp),
              contentAlignment = Alignment.Center
            ) {
              Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("SPEND MANAGED", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.outline)
                Text(user.spendManaged, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface, fontFamily = FontFamily.Monospace)
                Text(user.spendDetail, fontSize = 10.sp, color = EmeraldDark, fontWeight = FontWeight.Medium)
              }
            }
          }
        }
      }
    }

    // Travel Entitlement & Authority
    item {
      Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
          ) {
            Icon(Icons.Filled.Policy, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(18.dp))
            Text("Travel Entitlement & Authority", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
          }
          Text("Tier: MSI Global Elite", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }

        Card(
          shape = RoundedCornerShape(16.dp),
          colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerLowest),
          elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
          modifier = Modifier.fillMaxWidth()
        ) {
          Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
          ) {
            // Entitlement Badge Row
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
                verticalAlignment = Alignment.Top
              ) {
                Row(
                  verticalAlignment = Alignment.Top,
                  horizontalArrangement = Arrangement.spacedBy(10.dp),
                  modifier = Modifier.weight(1f)
                ) {
                  Box(
                    modifier = Modifier
                      .size(36.dp)
                      .clip(RoundedCornerShape(8.dp))
                      .background(MaterialTheme.colorScheme.primary),
                    contentAlignment = Alignment.Center
                  ) {
                    Icon(Icons.Filled.Stars, contentDescription = null, tint = Color.White, modifier = Modifier.size(20.dp))
                  }
                  Column {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                      Text(user.entitlementBand, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                      Box(
                        modifier = Modifier
                          .clip(RoundedCornerShape(4.dp))
                          .background(PrimaryFixed)
                          .padding(horizontal = 5.dp, vertical = 1.dp)
                      ) {
                        Text("Root Admin", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = OnPrimaryFixed)
                      }
                    }
                    Text(user.entitlementDetail, fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.padding(top = 2.dp))
                  }
                }

                Box(
                  modifier = Modifier
                    .clip(RoundedCornerShape(100.dp))
                    .background(EmeraldLight)
                    .padding(horizontal = 8.dp, vertical = 3.dp)
                ) {
                  Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    Box(modifier = Modifier.size(5.dp).clip(CircleShape).background(EmeraldGreen))
                    Text("Auto-Approve", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = EmeraldDark)
                  }
                }
              }
            }

            // Approval Bypass Row
            Box(
              modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(MaterialTheme.colorScheme.surfaceContainerLowest)
                .border(1.dp, MaterialTheme.colorScheme.surfaceContainer, RoundedCornerShape(8.dp))
                .padding(10.dp)
            ) {
              Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
              ) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                  Icon(Icons.Filled.LockOpen, contentDescription = null, tint = AmberNotice, modifier = Modifier.size(18.dp))
                  Column {
                    Text("Approval Bypass Privileges", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                    Text("Desk Root Admin — Instant dispatch authorized", fontSize = 11.sp, color = MaterialTheme.colorScheme.outline)
                  }
                }
                Text("ENABLED", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
              }
            }

            // Designated Reporting Manager
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
              Text("DESIGNATED REPORTING MANAGER", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.outline)
              Box(
                modifier = Modifier
                  .fillMaxWidth()
                  .clip(RoundedCornerShape(10.dp))
                .background(MaterialTheme.colorScheme.surfaceContainerLow)
                .padding(10.dp)
              ) {
                Row(
                  modifier = Modifier.fillMaxWidth(),
                  horizontalArrangement = Arrangement.SpaceBetween,
                  verticalAlignment = Alignment.CenterVertically
                ) {
                  Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    Box(
                      modifier = Modifier
                        .size(38.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.surfaceContainerHighest),
                      contentAlignment = Alignment.Center
                    ) {
                      Text("AP", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    }
                    Column {
                      Text(user.managerName, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                      Text(user.managerRole, fontSize = 11.sp, color = MaterialTheme.colorScheme.secondary)
                      Text(user.managerEmail, fontSize = 11.sp, color = MaterialTheme.colorScheme.primary)
                    }
                  }

                  Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    IconButton(
                      onClick = { repository.showToast("Opening email to ${user.managerEmail}") },
                      modifier = Modifier.size(34.dp).clip(CircleShape).background(MaterialTheme.colorScheme.surfaceContainerLowest)
                    ) {
                      Icon(Icons.Outlined.Mail, contentDescription = "Email", tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(16.dp))
                    }
                    IconButton(
                      onClick = { repository.showToast("Calling manager: ${user.managerPhone}") },
                      modifier = Modifier.size(34.dp).clip(CircleShape).background(MaterialTheme.colorScheme.surfaceContainerLowest)
                    ) {
                      Icon(Icons.Outlined.Phone, contentDescription = "Phone", tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(16.dp))
                    }
                  }
                }
              }
            }
          }
        }
      }
    }

    // Corporate Travel & Lodging Policy Caps
    item {
      Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
          ) {
            Icon(Icons.Filled.AccountBalanceWallet, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(18.dp))
            Text("Corporate Policy Caps", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
          }
          Box(
            modifier = Modifier
              .clip(RoundedCornerShape(100.dp))
              .background(PrimaryFixed)
              .padding(horizontal = 8.dp, vertical = 2.dp)
          ) {
            Text("USD COMPLIANCE MANDATORY", fontSize = 9.sp, fontWeight = FontWeight.ExtraBold, color = OnPrimaryFixed)
          }
        }

        Card(
          shape = RoundedCornerShape(16.dp),
          colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerLowest),
          elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
          modifier = Modifier.fillMaxWidth()
        ) {
          Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
          ) {
            // Flight Caps Header
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                Box(
                  modifier = Modifier.size(26.dp).clip(RoundedCornerShape(6.dp)).background(MaterialTheme.colorScheme.primary),
                  contentAlignment = Alignment.Center
                ) {
                  Icon(Icons.Filled.Flight, contentDescription = null, tint = Color.White, modifier = Modifier.size(15.dp))
                }
                Text("Flight Fare Policy Caps", fontWeight = FontWeight.Bold, fontSize = 13.sp)
              }
              Text("PER PASSENGER", fontSize = 10.sp, color = MaterialTheme.colorScheme.outline, fontWeight = FontWeight.Bold)
            }

            // Domestic Grid
            Box(
              modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp))
                .background(MaterialTheme.colorScheme.surfaceContainerLow)
                .padding(10.dp)
            ) {
              Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Row(
                  modifier = Modifier.fillMaxWidth(),
                  horizontalArrangement = Arrangement.SpaceBetween
                ) {
                  Text("DOMESTIC (US) FLIGHTS", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                  Text("Pre-Approved Standard", fontSize = 10.sp, color = MaterialTheme.colorScheme.outline)
                }

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                  Box(
                    modifier = Modifier.weight(1f).clip(RoundedCornerShape(6.dp)).background(MaterialTheme.colorScheme.surfaceContainerLowest).padding(8.dp)
                  ) {
                    Column {
                      Text("One Way", fontSize = 10.sp, color = MaterialTheme.colorScheme.outline)
                      Text("USD 400", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary, fontFamily = FontFamily.Monospace)
                      Text("Max cap", fontSize = 9.sp, color = MaterialTheme.colorScheme.outline)
                    }
                  }
                  Box(
                    modifier = Modifier.weight(1f).clip(RoundedCornerShape(6.dp)).background(MaterialTheme.colorScheme.surfaceContainerLowest).padding(8.dp)
                  ) {
                    Column {
                      Text("Round Trip", fontSize = 10.sp, color = MaterialTheme.colorScheme.outline)
                      Text("USD 800", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary, fontFamily = FontFamily.Monospace)
                      Text("Max cap", fontSize = 9.sp, color = MaterialTheme.colorScheme.outline)
                    }
                  }
                  Box(
                    modifier = Modifier.weight(1f).clip(RoundedCornerShape(6.dp)).background(MaterialTheme.colorScheme.surfaceContainerLowest).padding(8.dp)
                  ) {
                    Column {
                      Text("Multi-City", fontSize = 10.sp, color = MaterialTheme.colorScheme.outline)
                      Text("USD 1,200", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary, fontFamily = FontFamily.Monospace)
                      Text("Min 3 legs", fontSize = 9.sp, color = AmberNotice)
                    }
                  }
                }
              }
            }

            // International Grid
            Box(
              modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp))
                .background(MaterialTheme.colorScheme.surfaceContainerLow)
                .padding(10.dp)
            ) {
              Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Row(
                  modifier = Modifier.fillMaxWidth(),
                  horizontalArrangement = Arrangement.SpaceBetween
                ) {
                  Text("INTERNATIONAL FLIGHTS (ROUND TRIP)", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                  Text("Per Person Max", fontSize = 10.sp, color = MaterialTheme.colorScheme.outline)
                }

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                  Box(
                    modifier = Modifier.weight(1f).clip(RoundedCornerShape(6.dp)).background(MaterialTheme.colorScheme.surfaceContainerLowest).padding(8.dp)
                  ) {
                    Column {
                      Text("Economy", fontSize = 10.sp, color = MaterialTheme.colorScheme.outline)
                      Text("USD 2,000", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary, fontFamily = FontFamily.Monospace)
                      Text("Standard", fontSize = 9.sp, color = MaterialTheme.colorScheme.outline)
                    }
                  }
                  Box(
                    modifier = Modifier.weight(1f).clip(RoundedCornerShape(6.dp)).background(MaterialTheme.colorScheme.surfaceContainerLowest).padding(8.dp)
                  ) {
                    Column {
                      Text("Premium Eco", fontSize = 10.sp, color = MaterialTheme.colorScheme.outline)
                      Text("USD 3,000", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary, fontFamily = FontFamily.Monospace)
                      Text("Long-haul", fontSize = 9.sp, color = MaterialTheme.colorScheme.outline)
                    }
                  }
                  Box(
                    modifier = Modifier.weight(1f).clip(RoundedCornerShape(6.dp)).background(MaterialTheme.colorScheme.surfaceContainerLowest).border(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.4f), RoundedCornerShape(6.dp)).padding(8.dp)
                  ) {
                    Column {
                      Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                        Text("Business", fontSize = 10.sp, fontWeight = FontWeight.SemiBold)
                        Icon(Icons.Filled.Stars, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(12.dp))
                      }
                      Text("USD 5,000", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary, fontFamily = FontFamily.Monospace)
                      Text("Band A", fontSize = 9.sp, color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
                    }
                  }
                }
              }
            }

            // Hotel Allowance Row
            Divider(color = MaterialTheme.colorScheme.surfaceContainer)

            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                Box(
                  modifier = Modifier.size(26.dp).clip(RoundedCornerShape(6.dp)).background(MaterialTheme.colorScheme.primary),
                  contentAlignment = Alignment.Center
                ) {
                  Icon(Icons.Filled.Hotel, contentDescription = null, tint = Color.White, modifier = Modifier.size(15.dp))
                }
                Text("Hotel & Lodging Allowance Cap", fontWeight = FontWeight.Bold, fontSize = 13.sp)
              }
              Box(
                modifier = Modifier.clip(RoundedCornerShape(100.dp)).background(EmeraldLight).padding(horizontal = 6.dp, vertical = 2.dp)
              ) {
                Text("Active Policy", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = EmeraldDark)
              }
            }

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
                Column {
                  Text("Maximum Room Rate Allowance", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                  Text("Standard luxury or business partner hotel reservation", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
                Column(horizontalAlignment = Alignment.End) {
                  Text("USD 180", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary, fontFamily = FontFamily.Monospace)
                  Text("per night / adult", fontSize = 10.sp, color = MaterialTheme.colorScheme.outline)
                }
              }
            }

            // Governance note
            Box(
              modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(AmberNoticeLight)
                .padding(10.dp)
            ) {
              Row(verticalAlignment = Alignment.Top, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                Icon(Icons.Filled.Info, contentDescription = null, tint = AmberNotice, modifier = Modifier.size(16.dp))
                Text(
                  text = "Important: Reflects on user profile prior to travel request submission. Any over-budget selection requires Lead Dispatcher / Root Admin approval.",
                  fontSize = 11.sp,
                  color = AmberText,
                  lineHeight = 15.sp
                )
              }
            }
          }
        }
      }
    }

    // Travel & Booking Preferences
    item {
      Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
          ) {
            Icon(Icons.Filled.Tune, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(18.dp))
            Text("Travel & Booking Preferences", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
          }
          Text(
            text = "Edit",
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.clickable { showPreferencesEditModal = true }
          )
        }

        Card(
          shape = RoundedCornerShape(16.dp),
          colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerLowest),
          elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
          modifier = Modifier.fillMaxWidth()
        ) {
          Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
          ) {
            // Aviation Subsection
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                Icon(Icons.Filled.FlightTakeoff, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(18.dp))
                Text("Flight & Airline Preferences", fontWeight = FontWeight.Bold, fontSize = 13.sp)
              }
              Box(
                modifier = Modifier.clip(RoundedCornerShape(100.dp)).background(MaterialTheme.colorScheme.surfaceContainer).padding(horizontal = 6.dp, vertical = 2.dp)
              ) {
                Text("AVIATION", fontSize = 9.sp, color = MaterialTheme.colorScheme.onSurfaceVariant, fontWeight = FontWeight.Bold)
              }
            }

            // Carriers Pills
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
              Text("Preferred Airlines", fontSize = 11.sp, color = MaterialTheme.colorScheme.outline)
              Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                user.preferredAirlines.forEachIndexed { idx, airline ->
                  val dotColor = when (idx) {
                    0 -> Color(0xFF2563EB)
                    1 -> Color(0xFFDC2626)
                    else -> Color(0xFF7C3AED)
                  }
                  Box(
                    modifier = Modifier.clip(RoundedCornerShape(6.dp)).background(MaterialTheme.colorScheme.surfaceContainerLow).padding(horizontal = 8.dp, vertical = 4.dp)
                  ) {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                      Box(modifier = Modifier.size(6.dp).clip(CircleShape).background(dotColor))
                      Text(airline, fontSize = 11.sp, fontWeight = FontWeight.Medium)
                    }
                  }
                }
              }
            }

            // Frequent Flyer Accounts
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
              Text("Frequent Flyer Accounts", fontSize = 11.sp, color = MaterialTheme.colorScheme.outline)
              Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                user.frequentFlyerAccounts.forEach { (program, number) ->
                  Box(
                    modifier = Modifier.weight(1f).clip(RoundedCornerShape(8.dp)).background(MaterialTheme.colorScheme.surfaceContainerLow).padding(8.dp)
                  ) {
                    Column {
                      Text(program, fontSize = 10.sp, color = MaterialTheme.colorScheme.outline)
                      Text(number, fontSize = 11.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace)
                    }
                  }
                }
              }
            }

            // In-Cabin Perks
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
              Box(
                modifier = Modifier.weight(1f).clip(RoundedCornerShape(8.dp)).background(MaterialTheme.colorScheme.surfaceContainerLow).padding(8.dp)
              ) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                  Icon(Icons.Filled.AirlineSeatReclineExtra, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(18.dp))
                  Column {
                    Text("Seat Preference", fontSize = 9.sp, color = MaterialTheme.colorScheme.outline)
                    Text("Window Preferred", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                  }
                }
              }
              Box(
                modifier = Modifier.weight(1f).clip(RoundedCornerShape(8.dp)).background(MaterialTheme.colorScheme.surfaceContainerLow).padding(8.dp)
              ) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                  Icon(Icons.Filled.Restaurant, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(18.dp))
                  Column {
                    Text("Special Meal", fontSize = 9.sp, color = MaterialTheme.colorScheme.outline)
                    Text("Veg Hindu (AVML)", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                  }
                }
              }
            }

            Divider(color = MaterialTheme.colorScheme.surfaceContainer)

            // Hospitality Subsection
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                Icon(Icons.Filled.Hotel, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(18.dp))
                Text("Hotel & Lodging Preferences", fontWeight = FontWeight.Bold, fontSize = 13.sp)
              }
              Box(
                modifier = Modifier.clip(RoundedCornerShape(100.dp)).background(MaterialTheme.colorScheme.surfaceContainer).padding(horizontal = 6.dp, vertical = 2.dp)
              ) {
                Text("HOSPITALITY", fontSize = 9.sp, color = MaterialTheme.colorScheme.onSurfaceVariant, fontWeight = FontWeight.Bold)
              }
            }

            // Hotel Loyalties
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
              user.hotelLoyalties.forEach { (program, number) ->
                Box(
                  modifier = Modifier.weight(1f).clip(RoundedCornerShape(8.dp)).background(MaterialTheme.colorScheme.surfaceContainerLow).padding(8.dp)
                ) {
                  Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                  ) {
                    Column {
                      Text(program, fontSize = 10.sp, color = MaterialTheme.colorScheme.outline)
                      Text(number, fontSize = 12.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace)
                    }
                    Icon(Icons.Filled.HotelClass, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(18.dp))
                  }
                }
              }
            }

            // Default Room Tags
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
              Text("Default Room Configuration", fontSize = 11.sp, color = MaterialTheme.colorScheme.outline)
              Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                user.defaultRoomTags.forEach { tag ->
                  Box(
                    modifier = Modifier.clip(RoundedCornerShape(6.dp)).background(MaterialTheme.colorScheme.surfaceContainerLow).padding(horizontal = 8.dp, vertical = 4.dp)
                  ) {
                    Text(tag, fontSize = 10.sp, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.onSurface)
                  }
                }
              }
            }
          }
        }
      }
    }

    // System Sync & Dispatch Governance
    item {
      Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
          ) {
            Icon(Icons.Filled.Security, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(18.dp))
            Text("System Sync & Dispatch Governance", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
          }
          Box(
            modifier = Modifier.clip(RoundedCornerShape(100.dp)).background(EmeraldLight).padding(horizontal = 6.dp, vertical = 2.dp)
          ) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
              Box(modifier = Modifier.size(5.dp).clip(CircleShape).background(EmeraldGreen))
              Text("Live Sync Active", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = EmeraldDark)
            }
          }
        }

        Card(
          shape = RoundedCornerShape(16.dp),
          colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerLowest),
          elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
          modifier = Modifier.fillMaxWidth()
        ) {
          Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
          ) {
            // Workday Sync
            Box(
              modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp))
                .background(MaterialTheme.colorScheme.surfaceContainerLow)
                .padding(10.dp)
            ) {
              Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
              ) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                  Box(
                    modifier = Modifier.size(32.dp).clip(RoundedCornerShape(6.dp)).background(MaterialTheme.colorScheme.surfaceContainerLowest),
                    contentAlignment = Alignment.Center
                  ) {
                    Icon(Icons.Filled.SyncAlt, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(16.dp))
                  }
                  Column {
                    Text("Workday & SAP HRMS Sync", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                    Text("Synced today at 09:30 AM • Automated 12h cycle", fontSize = 10.sp, color = MaterialTheme.colorScheme.outline)
                  }
                }

                IconButton(
                  onClick = {
                    repository.syncWorkday()
                    // reset after brief delay
                    repository.finishWorkdaySync()
                  },
                  modifier = Modifier.size(32.dp).clip(CircleShape).background(MaterialTheme.colorScheme.surfaceContainerLowest)
                ) {
                  Icon(Icons.Filled.Refresh, contentDescription = "Sync", tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(16.dp))
                }
              }
            }

            // MFA Status
            Box(
              modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp))
                .background(MaterialTheme.colorScheme.surfaceContainerLow)
                .padding(10.dp)
            ) {
              Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
              ) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                  Box(
                    modifier = Modifier.size(32.dp).clip(RoundedCornerShape(6.dp)).background(MaterialTheme.colorScheme.surfaceContainerLowest),
                    contentAlignment = Alignment.Center
                  ) {
                    Icon(Icons.Filled.PhonelinkLock, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(16.dp))
                  }
                  Column {
                    Text("Multi-Factor Authentication (MFA)", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                    Text("Microsoft Authenticator • Hardware Token Active", fontSize = 10.sp, color = MaterialTheme.colorScheme.outline)
                  }
                }

                Icon(Icons.Filled.VerifiedUser, contentDescription = null, tint = EmeraldGreen, modifier = Modifier.size(18.dp))
              }
            }

            // Mirror to Travel Desk Queue Switch
            Box(
              modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp))
                .background(MaterialTheme.colorScheme.surfaceContainerLow)
                .padding(10.dp)
            ) {
              Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
              ) {
                Row(
                  verticalAlignment = Alignment.CenterVertically,
                  horizontalArrangement = Arrangement.spacedBy(10.dp),
                  modifier = Modifier.weight(1f)
                ) {
                  Box(
                    modifier = Modifier.size(32.dp).clip(RoundedCornerShape(6.dp)).background(MaterialTheme.colorScheme.surfaceContainerLowest),
                    contentAlignment = Alignment.Center
                  ) {
                    Icon(Icons.Filled.ForwardToInbox, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(16.dp))
                  }
                  Column {
                    Text("Mirror to Travel Desk Queue", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                    Text("Group_MSI.TravelDesk@msisurfaces.com", fontSize = 10.sp, color = MaterialTheme.colorScheme.outline)
                  }
                }

                Switch(
                  checked = user.mirrorQueueActive,
                  onCheckedChange = { repository.toggleMirrorQueue() },
                  colors = SwitchDefaults.colors(checkedThumbColor = Color.White, checkedTrackColor = MaterialTheme.colorScheme.primary)
                )
              }
            }
          }
        }
      }
    }

    // Executive Action Suite
    item {
      Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Button(
          onClick = { showPreferencesEditModal = true },
          modifier = Modifier.fillMaxWidth().height(48.dp).testTag("modify_preferences_btn"),
          shape = RoundedCornerShape(12.dp),
          colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
        ) {
          Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            Icon(Icons.Filled.EditNote, contentDescription = null, modifier = Modifier.size(18.dp))
            Text("Modify Travel & Loyalty Preferences", fontWeight = FontWeight.Bold, fontSize = 13.sp)
          }
        }

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
          Button(
            onClick = { showIdCardModal = true },
            modifier = Modifier.weight(1f).height(44.dp).testTag("travel_id_card_btn"),
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.surfaceContainerLowest, contentColor = MaterialTheme.colorScheme.onSurface),
            border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.4f))
          ) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
              Icon(Icons.Filled.CreditCard, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(16.dp))
              Text("Travel ID Card", fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
            }
          }

          Button(
            onClick = { repository.showToast("Booking history: 24 completed itineraries synchronized.") },
            modifier = Modifier.weight(1f).height(44.dp).testTag("booking_history_btn"),
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.surfaceContainerLowest, contentColor = MaterialTheme.colorScheme.onSurface),
            border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.4f))
          ) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
              Icon(Icons.Filled.History, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(16.dp))
              Text("Booking History", fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
            }
          }
        }

        // Corporate Log Out
        Button(
          onClick = onLogoutRequested,
          modifier = Modifier.fillMaxWidth().height(44.dp).testTag("logout_btn"),
          shape = RoundedCornerShape(10.dp),
          colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.errorContainer, contentColor = MaterialTheme.colorScheme.onErrorContainer)
        ) {
          Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            Icon(Icons.Filled.Logout, contentDescription = null, modifier = Modifier.size(16.dp))
            Text("Log Out of MSI Corporate SSO", fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
          }
        }

        // Footprint
        Column(
          modifier = Modifier.fillMaxWidth().padding(top = 10.dp),
          horizontalAlignment = Alignment.CenterHorizontally,
          verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
          Text("MSI SERVICES • CORPORATE ENTERPRISE DESK", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.outline, letterSpacing = 0.8.sp)
          Text("Version 4.18.2 (Build 9042) • SOC2 Type II Certified", fontSize = 11.sp, color = MaterialTheme.colorScheme.outline, fontFamily = FontFamily.Monospace)
        }
      }
    }
  }
}
