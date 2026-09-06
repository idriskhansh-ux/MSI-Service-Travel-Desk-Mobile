package com.example.ui.screens

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.data.TravelRepository
import com.example.ui.components.BoardingPassQrDialog
import com.example.ui.components.HotelCheckInDialog
import com.example.ui.theme.*

@Composable
fun DashboardScreen(
  repository: TravelRepository,
  modifier: Modifier = Modifier
) {
  val activeMission by repository.activeMission.collectAsState()
  val pastTrips by repository.pastTrips.collectAsState()
  val context = LocalContext.current

  var showFlightQrModal by remember { mutableStateOf(false) }
  var showHotelInfoModal by remember { mutableStateOf(false) }

  if (showFlightQrModal) {
    BoardingPassQrDialog(
      pnr = activeMission.flightVoucher.pnr,
      flight = activeMission.flightVoucher.flightNumber,
      passenger = "Rahul Sharma • ${activeMission.flightVoucher.seat}",
      onDismiss = { showFlightQrModal = false }
    )
  }

  if (showHotelInfoModal) {
    HotelCheckInDialog(
      hotelName = activeMission.hotelVoucher.propertyName,
      confirmation = activeMission.hotelVoucher.confirmationNumber,
      onDismiss = { showHotelInfoModal = false }
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
    // Top Executive Welcome Banner
    item {
      Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Box(
            modifier = Modifier
              .clip(RoundedCornerShape(100.dp))
              .background(MaterialTheme.colorScheme.surfaceContainer)
              .padding(horizontal = 10.dp, vertical = 4.dp)
          ) {
            Row(
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
              Box(
                modifier = Modifier
                  .size(7.dp)
                  .clip(CircleShape)
                  .background(MaterialTheme.colorScheme.primary)
              )
              Text(
                text = "MSI Concierge Portal",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
              )
            }
          }

          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
          ) {
            Icon(
              Icons.Filled.Sync,
              contentDescription = null,
              tint = MaterialTheme.colorScheme.primary,
              modifier = Modifier.size(16.dp)
            )
            Text(
              text = "Dual-Sync Ready",
              fontSize = 11.sp,
              color = MaterialTheme.colorScheme.secondary
            )
          }
        }

        Column {
          Text(
            text = "Welcome, Rahul Sharma",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
          )
          Text(
            text = "Lead Data Engineer • Employee ID #MSI-8841",
            fontSize = 13.sp,
            color = MaterialTheme.colorScheme.secondary
          )
        }

        // Verified Dispatch Indicator Pill
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.surfaceContainerLow)
            .padding(12.dp)
        ) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
          ) {
            Box(
              modifier = Modifier
                .size(34.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(MaterialTheme.colorScheme.surfaceContainerHighest),
              contentAlignment = Alignment.Center
            ) {
              Icon(
                Icons.Filled.VerifiedUser,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(18.dp)
              )
            }
            Column(modifier = Modifier.weight(1f)) {
              Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
              ) {
                Text(
                  text = "Dispatched by Idrish Khan (Super Admin)",
                  fontSize = 12.sp,
                  fontWeight = FontWeight.SemiBold,
                  color = MaterialTheme.colorScheme.onSurface,
                  maxLines = 1,
                  overflow = TextOverflow.Ellipsis
                )
                Text(
                  text = "Instant Offline",
                  fontSize = 10.sp,
                  fontWeight = FontWeight.Bold,
                  color = MaterialTheme.colorScheme.primary
                )
              }
              Text(
                text = "Synced to Mobile App & Web Portal • All vouchers verified",
                fontSize = 11.sp,
                color = MaterialTheme.colorScheme.secondary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
              )
            }
          }
        }
      }
    }

    // Active Corporate Mission Card
    item {
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
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
          ) {
            Column {
              Text(
                text = "ACTIVE CORPORATE MISSION",
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                letterSpacing = 0.8.sp
              )
              Text(
                text = "${activeMission.originCode} ⇄ ${activeMission.destCode}",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
              )
              Text(
                text = "${activeMission.originCity} to ${activeMission.destCity}",
                fontSize = 13.sp,
                color = MaterialTheme.colorScheme.secondary
              )
            }

            Box(
              modifier = Modifier
                .clip(RoundedCornerShape(100.dp))
                .background(MaterialTheme.colorScheme.surfaceContainer)
                .padding(horizontal = 10.dp, vertical = 4.dp)
            ) {
              Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(5.dp)
              ) {
                Box(
                  modifier = Modifier
                    .size(6.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary)
                )
                Text(
                  text = "Vouchers Issued",
                  fontSize = 11.sp,
                  fontWeight = FontWeight.Bold,
                  color = MaterialTheme.colorScheme.primary
                )
              }
            }
          }

          // Hero Image: Chicago Downtown Skyline
          Box(
            modifier = Modifier
              .fillMaxWidth()
              .height(130.dp)
              .clip(RoundedCornerShape(12.dp))
          ) {
            AsyncImage(
              model = "https://lh3.googleusercontent.com/aida-public/AB6AXuD3E8VzYTs7BU1B42OcSNvSSEuvnse4KRUS1KbVvpCusMiZmBSSbr4ZQ0ZdaS04K26JTxrCx_0DeUCkZngEDsqdwjlcDFqpiHdHo6aHioxFi0Tmz0R_8Jhc3OWCN-a5vw-t0gySl_RUrLIdP9k_-jNUOtXnc-PwNO57_9JCI2tNlOIIOVFFP8WQ4e_k9bumFuqq7fi-RCdHVJwNguSi_UlpMX1SgEzKZTDBIWvmJqVmU3dyRtw_An2b",
              contentDescription = "Chicago Downtown Skyline",
              contentScale = ContentScale.Crop,
              modifier = Modifier.fillMaxSize()
            )
            // Gradient Overlay with Mission Details
            Box(
              modifier = Modifier
                .fillMaxSize()
                .background(
                  Brush.verticalGradient(
                    listOf(Color.Transparent, Color(0x99111416), Color(0xCC111416))
                  )
                )
                .padding(12.dp),
              contentAlignment = Alignment.BottomStart
            ) {
              Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
              ) {
                Row(
                  verticalAlignment = Alignment.CenterVertically,
                  horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                  Icon(
                    Icons.Outlined.CalendarMonth,
                    contentDescription = null,
                    tint = PrimaryFixedDim,
                    modifier = Modifier.size(16.dp)
                  )
                  Text(
                    text = activeMission.dates,
                    fontSize = 12.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Medium
                  )
                }
                Box(
                  modifier = Modifier
                    .clip(RoundedCornerShape(4.dp))
                    .background(Color.Black.copy(alpha = 0.6f))
                    .padding(horizontal = 8.dp, vertical = 2.dp)
                ) {
                  Text(
                    text = activeMission.duration,
                    fontSize = 10.sp,
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold
                  )
                }
              }
            }
          }

          // Trip Purpose
          Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Row(
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.spacedBy(5.dp)
            ) {
              Icon(
                Icons.Filled.AdsClick,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(15.dp)
              )
              Text(
                text = "Trip Purpose:",
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurfaceVariant
              )
            }
            Box(
              modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(MaterialTheme.colorScheme.surfaceContainerLow)
                .padding(10.dp)
            ) {
              Text(
                text = activeMission.purpose,
                fontSize = 13.sp,
                color = MaterialTheme.colorScheme.onSurface,
                lineHeight = 18.sp
              )
            }
          }

          // Cost Allocation Bar
          Box(
            modifier = Modifier
              .fillMaxWidth()
              .clip(RoundedCornerShape(8.dp))
              .background(MaterialTheme.colorScheme.surfaceContainerLow.copy(alpha = 0.7f))
              .padding(10.dp)
          ) {
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Column {
                Text("Total Settled Cost", fontSize = 10.sp, color = MaterialTheme.colorScheme.secondary)
                Text(
                  "$${activeMission.settledCostUsd} USD",
                  fontSize = 16.sp,
                  fontWeight = FontWeight.Bold,
                  color = MaterialTheme.colorScheme.onSurface
                )
              }
              Column(horizontalAlignment = Alignment.End) {
                Text("Corporate Policy Cap", fontSize = 10.sp, color = MaterialTheme.colorScheme.secondary)
                Text(
                  "$${activeMission.policyCapUsd} (Saved $${activeMission.savingsUsd})",
                  fontSize = 12.sp,
                  fontWeight = FontWeight.SemiBold,
                  color = MaterialTheme.colorScheme.primary
                )
              }
            }
          }
        }
      }
    }

    // Confirmed Flight Voucher
    item {
      val flight = activeMission.flightVoucher
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
            Icon(Icons.Filled.ConfirmationNumber, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(18.dp))
            Text("Confirmed Flight Voucher", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
          }
          Text("Segment 01/01", fontSize = 11.sp, color = MaterialTheme.colorScheme.secondary)
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
            // Airline Carrier Row
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
                    .size(38.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(MaterialTheme.colorScheme.surfaceContainer),
                  contentAlignment = Alignment.Center
                ) {
                  Text(flight.carrierCode, fontWeight = FontWeight.Bold, fontSize = 14.sp, color = MaterialTheme.colorScheme.primary)
                }
                Column {
                  Text(flight.carrierName, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                  Text("${flight.flightNumber} • ${flight.flightClass}", fontSize = 11.sp, color = MaterialTheme.colorScheme.secondary)
                }
              }
              Box(
                modifier = Modifier
                  .clip(RoundedCornerShape(6.dp))
                  .background(MaterialTheme.colorScheme.surfaceContainer)
                  .padding(horizontal = 8.dp, vertical = 4.dp)
              ) {
                Text("PNR: ${flight.pnr}", fontSize = 12.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace)
              }
            }

            // Route Visualization Box
            Box(
              modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp))
                .background(MaterialTheme.colorScheme.surfaceContainerLow)
                .padding(12.dp)
            ) {
              Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
              ) {
                Column {
                  Text(flight.originCode, fontSize = 22.sp, fontWeight = FontWeight.Bold)
                  Text(flight.originTerminal, fontSize = 10.sp, color = MaterialTheme.colorScheme.secondary)
                  Text(flight.originTime, fontSize = 12.sp, fontWeight = FontWeight.SemiBold, modifier = Modifier.padding(top = 2.dp))
                }

                Column(
                  horizontalAlignment = Alignment.CenterHorizontally,
                  modifier = Modifier.weight(1f).padding(horizontal = 8.dp)
                ) {
                  Text(flight.duration, fontSize = 10.sp, color = MaterialTheme.colorScheme.secondary)
                  Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth().padding(vertical = 2.dp)
                  ) {
                    Divider(modifier = Modifier.weight(1f), color = MaterialTheme.colorScheme.outlineVariant)
                    Icon(Icons.Filled.FlightTakeoff, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(16.dp).padding(horizontal = 2.dp))
                    Divider(modifier = Modifier.weight(1f), color = MaterialTheme.colorScheme.outlineVariant)
                  }
                  Text(flight.seat, fontSize = 10.sp, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.primary)
                }

                Column(horizontalAlignment = Alignment.End) {
                  Text(flight.destCode, fontSize = 22.sp, fontWeight = FontWeight.Bold)
                  Text(flight.destTerminal, fontSize = 10.sp, color = MaterialTheme.colorScheme.secondary)
                  Text(flight.destTime, fontSize = 12.sp, fontWeight = FontWeight.SemiBold, modifier = Modifier.padding(top = 2.dp))
                }
              }
            }

            // File Attachment Pill
            Box(
              modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(MaterialTheme.colorScheme.surfaceContainer)
                .padding(horizontal = 10.dp, vertical = 8.dp)
            ) {
              Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
              ) {
                Row(
                  verticalAlignment = Alignment.CenterVertically,
                  horizontalArrangement = Arrangement.spacedBy(8.dp),
                  modifier = Modifier.weight(1f)
                ) {
                  Icon(Icons.Filled.PictureAsPdf, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(20.dp))
                  Column {
                    Text(flight.fileName, fontSize = 12.sp, fontWeight = FontWeight.SemiBold, maxLines = 1, overflow = TextOverflow.Ellipsis)
                    Text(flight.fileSize, fontSize = 10.sp, color = MaterialTheme.colorScheme.secondary)
                  }
                }
                Box(
                  modifier = Modifier
                    .clip(RoundedCornerShape(4.dp))
                    .background(MaterialTheme.colorScheme.surfaceContainerLowest)
                    .padding(horizontal = 6.dp, vertical = 2.dp)
                ) {
                  Text("Ready", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                }
              }
            }

            // Actions
            Button(
              onClick = { repository.showToast("Downloading ${flight.fileName}...") },
              modifier = Modifier.fillMaxWidth().height(44.dp).testTag("download_flight_pdf"),
              shape = RoundedCornerShape(10.dp),
              colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
              Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                Icon(Icons.Filled.Download, contentDescription = null, modifier = Modifier.size(18.dp))
                Text("Download PDF E-Ticket", fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
              }
            }

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
              Button(
                onClick = { repository.showToast("Flight pass added to Apple/Google Wallet") },
                modifier = Modifier.weight(1f).height(40.dp).testTag("flight_wallet_btn"),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                  containerColor = MaterialTheme.colorScheme.surfaceContainer,
                  contentColor = MaterialTheme.colorScheme.onSurface
                )
              ) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                  Icon(Icons.Filled.AccountBalanceWallet, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(16.dp))
                  Text("Add to Wallet", fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                }
              }

              Button(
                onClick = { showFlightQrModal = true },
                modifier = Modifier.weight(1f).height(40.dp).testTag("flight_qr_btn"),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                  containerColor = MaterialTheme.colorScheme.surfaceContainer,
                  contentColor = MaterialTheme.colorScheme.onSurface
                )
              ) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                  Icon(Icons.Filled.QrCode2, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(16.dp))
                  Text("Digital Pass", fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                }
              }
            }
          }
        }
      }
    }

    // Confirmed Hotel Voucher
    item {
      val hotel = activeMission.hotelVoucher
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
            Icon(Icons.Filled.Hotel, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(18.dp))
            Text("Confirmed Hotel Voucher", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
          }
          Text("3 Nights Stay", fontSize = 11.sp, color = MaterialTheme.colorScheme.secondary)
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
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.Top
            ) {
              Column {
                Text(hotel.propertyName, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                Text(hotel.location, fontSize = 12.sp, color = MaterialTheme.colorScheme.secondary)
              }
              Box(
                modifier = Modifier
                  .clip(RoundedCornerShape(6.dp))
                  .background(MaterialTheme.colorScheme.surfaceContainer)
                  .padding(horizontal = 8.dp, vertical = 4.dp)
              ) {
                Text(hotel.confirmationNumber, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary, fontFamily = FontFamily.Monospace)
              }
            }

            // Hotel Room Photo
            Box(
              modifier = Modifier
                .fillMaxWidth()
                .height(130.dp)
                .clip(RoundedCornerShape(10.dp))
            ) {
              AsyncImage(
                model = "https://lh3.googleusercontent.com/aida-public/AB6AXuBjGQx1vga_QHuF769PkqgYH2lBaYlltSnwyyImGp8i1YFwR5Bz2lRvx0dewnrjBTzMki-Va6-TOGAblgA2DbMWO97K3wwxS3zcM9O1qrumtOLItHGLBOhXbYM3hnHVTZ9--Ltt4-2e8Ugtg9bSTI86_epEVxTEo9OLuThLb5WJiEYpeZFran6LgqJ43cWz6074CQfAWndM_J52CKPph1X_UPHOuuryktsZ8LjnDT08rmhmCFxCCGEP",
                contentDescription = "Chicago Marriott Luxury Hotel Room",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
              )
              Box(
                modifier = Modifier
                  .align(Alignment.BottomStart)
                  .padding(8.dp)
                  .clip(RoundedCornerShape(6.dp))
                  .background(Color.Black.copy(alpha = 0.7f))
                  .padding(horizontal = 8.dp, vertical = 4.dp)
              ) {
                Text(hotel.roomDescription, fontSize = 10.sp, color = Color.White, fontWeight = FontWeight.SemiBold)
              }
            }

            // Stay Details Box
            Box(
              modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp))
                .background(MaterialTheme.colorScheme.surfaceContainerLow)
                .padding(12.dp)
            ) {
              Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Row(
                  modifier = Modifier.fillMaxWidth(),
                  horizontalArrangement = Arrangement.SpaceBetween
                ) {
                  Column {
                    Text("Check-In", fontSize = 10.sp, color = MaterialTheme.colorScheme.secondary)
                    Text(hotel.checkIn, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                  }
                  Column(horizontalAlignment = Alignment.End) {
                    Text("Check-Out", fontSize = 10.sp, color = MaterialTheme.colorScheme.secondary)
                    Text(hotel.checkOut, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                  }
                }

                Column {
                  Text("Room Preference", fontSize = 10.sp, color = MaterialTheme.colorScheme.secondary)
                  Text(hotel.roomPreference, fontSize = 12.sp, fontWeight = FontWeight.Medium)
                }

                Column {
                  Text("Corporate Rate Approved", fontSize = 10.sp, color = MaterialTheme.colorScheme.secondary)
                  Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(hotel.rateText, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                    Text(hotel.rateCapText, fontSize = 10.sp, color = MaterialTheme.colorScheme.secondary)
                  }
                }
              }
            }

            // File Attachment Pill
            Box(
              modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(MaterialTheme.colorScheme.surfaceContainer)
                .padding(horizontal = 10.dp, vertical = 8.dp)
            ) {
              Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
              ) {
                Row(
                  verticalAlignment = Alignment.CenterVertically,
                  horizontalArrangement = Arrangement.spacedBy(8.dp),
                  modifier = Modifier.weight(1f)
                ) {
                  Icon(Icons.Filled.PictureAsPdf, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(20.dp))
                  Column {
                    Text(hotel.fileName, fontSize = 12.sp, fontWeight = FontWeight.SemiBold, maxLines = 1, overflow = TextOverflow.Ellipsis)
                    Text(hotel.fileSize, fontSize = 10.sp, color = MaterialTheme.colorScheme.secondary)
                  }
                }
                Box(
                  modifier = Modifier
                    .clip(RoundedCornerShape(4.dp))
                    .background(MaterialTheme.colorScheme.surfaceContainerLowest)
                    .padding(horizontal = 6.dp, vertical = 2.dp)
                ) {
                  Text("Direct Billed", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                }
              }
            }

            // Actions
            Button(
              onClick = { repository.showToast("Downloading ${hotel.fileName}...") },
              modifier = Modifier.fillMaxWidth().height(44.dp).testTag("download_hotel_pdf"),
              shape = RoundedCornerShape(10.dp),
              colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
              Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                Icon(Icons.Filled.Download, contentDescription = null, modifier = Modifier.size(18.dp))
                Text("Download Hotel Voucher (PDF)", fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
              }
            }

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
              Button(
                onClick = { repository.showToast("Hotel digital key added to Wallet") },
                modifier = Modifier.weight(1f).height(40.dp).testTag("hotel_key_btn"),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                  containerColor = MaterialTheme.colorScheme.surfaceContainer,
                  contentColor = MaterialTheme.colorScheme.onSurface
                )
              ) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                  Icon(Icons.Filled.VpnKey, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(16.dp))
                  Text("Wallet Key", fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                }
              }

              Button(
                onClick = { showHotelInfoModal = true },
                modifier = Modifier.weight(1f).height(40.dp).testTag("hotel_info_btn"),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                  containerColor = MaterialTheme.colorScheme.surfaceContainer,
                  contentColor = MaterialTheme.colorScheme.onSurface
                )
              ) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                  Icon(Icons.Filled.Info, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(16.dp))
                  Text("Check-in QR", fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                }
              }
            }
          }
        }
      }
    }

    // Ground Transport & Direct Support Card
    item {
      Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
          Icon(Icons.Filled.LocalTaxi, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(18.dp))
          Text("Ground Transport & Direct Support", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
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
            // Uber Voucher Row
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
                    .size(40.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(MaterialTheme.colorScheme.inverseSurface),
                  contentAlignment = Alignment.Center
                ) {
                  Icon(Icons.Filled.DirectionsCar, contentDescription = null, tint = Color.White, modifier = Modifier.size(22.dp))
                }
                Column {
                  Text("Uber for Business Voucher", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                  Text(activeMission.uberAllowance, fontSize = 11.sp, color = MaterialTheme.colorScheme.secondary)
                }
              }

              Button(
                onClick = {
                  val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                  val clip = ClipData.newPlainText("Uber Code", activeMission.uberCode)
                  clipboard.setPrimaryClip(clip)
                  repository.showToast("Copied code: ${activeMission.uberCode}")
                },
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                  containerColor = MaterialTheme.colorScheme.surfaceContainerLow,
                  contentColor = MaterialTheme.colorScheme.primary
                ),
                contentPadding = PaddingValues(horizontal = 10.dp, vertical = 6.dp)
              ) {
                Row(
                  verticalAlignment = Alignment.CenterVertically,
                  horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                  Text(activeMission.uberCode, fontWeight = FontWeight.Bold, fontSize = 12.sp, fontFamily = FontFamily.Monospace)
                  Icon(Icons.Filled.ContentCopy, contentDescription = "Copy", modifier = Modifier.size(14.dp))
                }
              }
            }

            // MSI Admin On-Duty Row
            Box(
              modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp))
                .background(MaterialTheme.colorScheme.surfaceContainer)
                .padding(10.dp)
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
                      .background(PrimaryContainer),
                    contentAlignment = Alignment.Center
                  ) {
                    Text("IK", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                  }
                  Column {
                    Text("Idrish Khan", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                    Text("MSI Travel Desk Senior Admin • On-Duty", fontSize = 10.sp, color = MaterialTheme.colorScheme.secondary)
                  }
                }

                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                  IconButton(
                    onClick = { repository.showToast("Calling MSI Travel Desk Concierge: +1 (800) 555-0199") },
                    modifier = Modifier
                      .size(36.dp)
                      .clip(RoundedCornerShape(8.dp))
                      .background(MaterialTheme.colorScheme.surfaceContainerLowest)
                  ) {
                    Icon(Icons.Filled.Call, contentDescription = "Call", tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(18.dp))
                  }

                  IconButton(
                    onClick = { repository.showToast("Connecting directly with Admin Idrish Khan on chat...") },
                    modifier = Modifier
                      .size(36.dp)
                      .clip(RoundedCornerShape(8.dp))
                      .background(MaterialTheme.colorScheme.primary)
                  ) {
                    Icon(Icons.Filled.Chat, contentDescription = "Chat", tint = Color.White, modifier = Modifier.size(18.dp))
                  }
                }
              }
            }
          }
        }
      }
    }

    // Past Trips & Archived Vouchers
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
            Icon(Icons.Filled.History, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(18.dp))
            Text("Past Trips & Archived Vouchers", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
          }
          Text("2 Trips Completed", fontSize = 11.sp, color = MaterialTheme.colorScheme.secondary)
        }

        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
          pastTrips.forEach { trip ->
            Card(
              shape = RoundedCornerShape(12.dp),
              colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerLowest),
              elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
              modifier = Modifier.fillMaxWidth()
            ) {
              Row(
                modifier = Modifier
                  .fillMaxWidth()
                  .padding(12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
              ) {
                Column(modifier = Modifier.weight(1f).padding(end = 8.dp)) {
                  Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                  ) {
                    Text(trip.route, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                    Text("• ${trip.dates}", fontSize = 11.sp, color = MaterialTheme.colorScheme.secondary)
                  }
                  Text(trip.description, fontSize = 11.sp, color = MaterialTheme.colorScheme.secondary, maxLines = 1, overflow = TextOverflow.Ellipsis)
                }

                Button(
                  onClick = { repository.showToast("Exporting archive ${trip.zipFileName}...") },
                  shape = RoundedCornerShape(8.dp),
                  colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.surfaceContainer,
                    contentColor = MaterialTheme.colorScheme.onSurface
                  ),
                  contentPadding = PaddingValues(horizontal = 10.dp, vertical = 6.dp)
                ) {
                  Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                  ) {
                    Icon(Icons.Filled.Download, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(14.dp))
                    Text("${trip.vouchersCount} Vouchers", fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
                  }
                }
              }
            }
          }
        }
      }
    }

    // Universal Device Sync & Offline Protocol Banner
    item {
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .clip(RoundedCornerShape(12.dp))
          .background(MaterialTheme.colorScheme.surfaceContainerLow)
          .padding(14.dp)
      ) {
        Row(
          verticalAlignment = Alignment.Top,
          horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
          Icon(
            Icons.Filled.CloudDone,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(22.dp)
          )
          Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
            Text(
              text = "Universal Device Sync & Offline Protocol",
              fontWeight = FontWeight.Bold,
              fontSize = 12.sp,
              color = MaterialTheme.colorScheme.onSurface
            )
            Text(
              text = "All issued vouchers are accessible offline in your MSI mobile app and downloadable via web browser at travel.msisurfaces.com. Updates reflect across devices in real-time.",
              fontSize = 11.sp,
              color = MaterialTheme.colorScheme.secondary,
              lineHeight = 16.sp
            )
          }
        }
      }
    }
  }
}
