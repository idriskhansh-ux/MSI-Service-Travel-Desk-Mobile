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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.TravelRepository
import com.example.model.*
import com.example.ui.theme.*

@Composable
fun RaiseRequestScreen(
  repository: TravelRepository,
  modifier: Modifier = Modifier
) {
  val selectedReq by repository.selectedRequirement.collectAsState()
  val tripType by repository.selectedTripType.collectAsState()
  val flightForm by repository.flightForm.collectAsState()
  val hotelForm by repository.hotelForm.collectAsState()
  val user by repository.userProfile.collectAsState()

  LazyColumn(
    modifier = modifier
      .fillMaxSize()
      .background(MaterialTheme.colorScheme.surface)
      .padding(horizontal = 16.dp),
    verticalArrangement = Arrangement.spacedBy(16.dp),
    contentPadding = PaddingValues(top = 16.dp, bottom = 28.dp)
  ) {
    // Header & Dispatch Route Context
    item {
      Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text(
            text = "Raise Travel Request",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
          )
          Box(
            modifier = Modifier
              .clip(RoundedCornerShape(100.dp))
              .background(PrimaryFixed)
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
                  .background(PrimaryBronze)
              )
              Text(
                text = "DIRECT DISPATCH",
                fontSize = 10.sp,
                fontWeight = FontWeight.ExtraBold,
                color = OnPrimaryFixed,
                letterSpacing = 0.6.sp
              )
            }
          }
        }

        // Automated Routing Notice Pill
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.surfaceContainerLow)
            .padding(10.dp)
        ) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
          ) {
            Icon(
              Icons.Filled.ForwardToInbox,
              contentDescription = null,
              tint = MaterialTheme.colorScheme.primary,
              modifier = Modifier.size(20.dp)
            )
            Column {
              Text(
                text = "AUTOMATED ROUTING",
                fontSize = 9.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.outline,
                letterSpacing = 0.6.sp
              )
              Text(
                text = "Group_MSI.TravelDesk@msisurfaces.com",
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface
              )
            }
          }
        }

        // Employee Context Card
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
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
          ) {
            Box(
              modifier = Modifier
                .size(42.dp)
                .clip(CircleShape)
                .background(PrimaryFixed),
              contentAlignment = Alignment.Center
            ) {
              Text(
                text = "IK",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = OnPrimaryFixed
              )
            }

            Column(modifier = Modifier.weight(1f)) {
              Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
              ) {
                Text(user.name, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                Box(
                  modifier = Modifier
                    .clip(RoundedCornerShape(4.dp))
                    .background(MaterialTheme.colorScheme.surfaceContainer)
                    .padding(horizontal = 6.dp, vertical = 2.dp)
                ) {
                  Text(
                    user.empId,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,
                    fontFamily = FontFamily.Monospace
                  )
                }
              }
              Text(
                text = "Cost Center: ${user.costCenter}",
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
              )
              Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier.padding(top = 2.dp)
              ) {
                Icon(
                  Icons.Outlined.SupervisorAccount,
                  contentDescription = null,
                  tint = MaterialTheme.colorScheme.outline,
                  modifier = Modifier.size(13.dp)
                )
                Text(
                  text = "Manager: ${user.managerName} (${user.managerRole.split("•").firstOrNull()?.trim() ?: "Head of Engg"})",
                  fontSize = 10.sp,
                  color = MaterialTheme.colorScheme.outline
                )
              }
            }
          }
        }
      }
    }

    // 1. Select Requirement Selector
    item {
      Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
        Text(
          text = "1. SELECT REQUIREMENT",
          fontSize = 11.sp,
          fontWeight = FontWeight.Bold,
          color = MaterialTheme.colorScheme.onSurfaceVariant,
          letterSpacing = 0.6.sp
        )

        Row(
          modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.surfaceContainer)
            .padding(4.dp),
          horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
          TripRequirement.entries.forEach { req ->
            val isSelected = selectedReq == req
            Column(
              modifier = Modifier
                .weight(1f)
                .clip(RoundedCornerShape(8.dp))
                .background(
                  if (isSelected) MaterialTheme.colorScheme.surfaceContainerLowest else Color.Transparent
                )
                .clickable { repository.setRequirement(req) }
                .padding(vertical = 10.dp, horizontal = 4.dp)
                .testTag("req_${req.name.lowercase()}"),
              horizontalAlignment = Alignment.CenterHorizontally,
              verticalArrangement = Arrangement.Center
            ) {
              when (req) {
                TripRequirement.FLIGHTS_ONLY -> {
                  Icon(
                    Icons.Filled.Flight,
                    contentDescription = null,
                    tint = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.size(20.dp)
                  )
                }
                TripRequirement.HOTEL_ONLY -> {
                  Icon(
                    Icons.Filled.Hotel,
                    contentDescription = null,
                    tint = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.size(20.dp)
                  )
                }
                TripRequirement.BOTH -> {
                  Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(2.dp)
                  ) {
                    Icon(
                      Icons.Filled.Flight,
                      contentDescription = null,
                      tint = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant,
                      modifier = Modifier.size(16.dp)
                    )
                    Text(
                      "+",
                      fontSize = 11.sp,
                      fontWeight = FontWeight.Bold,
                      color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Icon(
                      Icons.Filled.Hotel,
                      contentDescription = null,
                      tint = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant,
                      modifier = Modifier.size(16.dp)
                    )
                  }
                }
              }
              Spacer(modifier = Modifier.height(2.dp))
              Text(
                text = req.label,
                fontSize = 11.sp,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.SemiBold,
                color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
              )
            }
          }
        }
      }
    }

    // 2. Trip Type Selector (Visible when Flight or Both selected)
    if (selectedReq != TripRequirement.HOTEL_ONLY) {
      item {
        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
          Text(
            text = "2. TRIP TYPE",
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            letterSpacing = 0.6.sp
          )

          Row(
            modifier = Modifier
              .fillMaxWidth()
              .clip(RoundedCornerShape(12.dp))
              .background(MaterialTheme.colorScheme.surfaceContainerLow)
              .padding(4.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
          ) {
            TripType.entries.forEach { type ->
              val isSelected = tripType == type
              Box(
                modifier = Modifier
                  .weight(1f)
                  .clip(RoundedCornerShape(8.dp))
                  .background(
                    if (isSelected) MaterialTheme.colorScheme.surfaceContainerLowest else Color.Transparent
                  )
                  .clickable { repository.setTripType(type) }
                  .padding(vertical = 10.dp)
                  .testTag("type_${type.name.lowercase()}"),
                contentAlignment = Alignment.Center
              ) {
                Text(
                  text = type.label,
                  fontSize = 13.sp,
                  fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                  color = if (isSelected) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.onSurfaceVariant
                )
              }
            }
          }
        }
      }
    }

    // 3. Flight Itinerary Specs Section (Shown if Flights or Both)
    if (selectedReq != TripRequirement.HOTEL_ONLY) {
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
            // Header
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
              ) {
                Box(
                  modifier = Modifier
                    .size(34.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(PrimaryFixed),
                  contentAlignment = Alignment.Center
                ) {
                  Icon(Icons.Filled.Airlines, contentDescription = null, tint = OnPrimaryFixed, modifier = Modifier.size(18.dp))
                }
                Column {
                  Text("Flight Itinerary Specs", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                  Text("Travel Desk issues lowest compliant logical fare", fontSize = 10.sp, color = MaterialTheme.colorScheme.outline)
                }
              }

              Box(
                modifier = Modifier
                  .clip(RoundedCornerShape(4.dp))
                  .background(MaterialTheme.colorScheme.surfaceContainerHigh)
                  .padding(horizontal = 6.dp, vertical = 2.dp)
              ) {
                Text("POLICY MANAGED", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurfaceVariant)
              }
            }

            // Route Inputs with Swap Button
            Column(
              verticalArrangement = Arrangement.spacedBy(6.dp),
              horizontalAlignment = Alignment.CenterHorizontally
            ) {
              // Origin Box
              Box(
                modifier = Modifier
                  .fillMaxWidth()
                  .clip(RoundedCornerShape(10.dp))
                  .background(MaterialTheme.colorScheme.surfaceContainerLow)
                  .padding(10.dp)
              ) {
                Column {
                  Text("ORIGIN DEPARTURE HUB", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.outline)
                  Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                  ) {
                    Row(
                      verticalAlignment = Alignment.CenterVertically,
                      horizontalArrangement = Arrangement.spacedBy(6.dp),
                      modifier = Modifier.weight(1f)
                    ) {
                      Icon(Icons.Filled.FlightTakeoff, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(18.dp))
                      Text(flightForm.origin, fontSize = 13.sp, fontWeight = FontWeight.SemiBold, maxLines = 1)
                    }
                    Box(
                      modifier = Modifier
                        .clip(RoundedCornerShape(4.dp))
                        .background(MaterialTheme.colorScheme.surfaceContainer)
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                      Text(flightForm.originCode, fontSize = 11.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace)
                    }
                  }
                }
              }

              // Airport Swap Button
              Box(
                modifier = Modifier
                  .size(32.dp)
                  .clip(CircleShape)
                  .background(MaterialTheme.colorScheme.surfaceContainerLowest)
                  .border(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f), CircleShape)
                  .clickable { repository.swapAirports() }
                  .testTag("swap_airports_btn"),
                contentAlignment = Alignment.Center
              ) {
                Icon(Icons.Filled.SwapVert, contentDescription = "Swap", tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(18.dp))
              }

              // Destination Box
              Box(
                modifier = Modifier
                  .fillMaxWidth()
                  .clip(RoundedCornerShape(10.dp))
                  .background(MaterialTheme.colorScheme.surfaceContainerLow)
                  .padding(10.dp)
              ) {
                Column {
                  Text("DESTINATION ARRIVAL HUB", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.outline)
                  Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                  ) {
                    Row(
                      verticalAlignment = Alignment.CenterVertically,
                      horizontalArrangement = Arrangement.spacedBy(6.dp),
                      modifier = Modifier.weight(1f)
                    ) {
                      Icon(Icons.Filled.FlightLand, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(18.dp))
                      Text(flightForm.destination, fontSize = 13.sp, fontWeight = FontWeight.SemiBold, maxLines = 1)
                    }
                    Box(
                      modifier = Modifier
                        .clip(RoundedCornerShape(4.dp))
                        .background(MaterialTheme.colorScheme.surfaceContainer)
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                      Text(flightForm.destinationCode, fontSize = 11.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace)
                    }
                  }
                }
              }
            }

            // Departure Window
            Box(
              modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp))
                .background(MaterialTheme.colorScheme.surfaceContainerLow)
                .padding(10.dp)
            ) {
              Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Row(
                  modifier = Modifier.fillMaxWidth(),
                  horizontalArrangement = Arrangement.SpaceBetween
                ) {
                  Text("OUTBOUND FLIGHT WINDOW", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.outline)
                  Text("Confirmed Schedule", fontSize = 10.sp, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.primary)
                }
                Row(
                  modifier = Modifier.fillMaxWidth(),
                  horizontalArrangement = Arrangement.SpaceBetween,
                  verticalAlignment = Alignment.CenterVertically
                ) {
                  Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    Icon(Icons.Outlined.CalendarMonth, contentDescription = null, tint = MaterialTheme.colorScheme.outline, modifier = Modifier.size(16.dp))
                    Text(flightForm.outboundDate, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                  }
                  Box(
                    modifier = Modifier
                      .clip(RoundedCornerShape(6.dp))
                      .background(MaterialTheme.colorScheme.surfaceContainerLowest)
                      .padding(horizontal = 8.dp, vertical = 4.dp)
                  ) {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                      Icon(Icons.Filled.WbSunny, contentDescription = null, tint = TertiaryBronze, modifier = Modifier.size(14.dp))
                      Text(flightForm.outboundTimeWindow, fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
                    }
                  }
                }
              }
            }

            // Return Window (if Round Trip)
            if (tripType == TripType.ROUND_TRIP) {
              Box(
                modifier = Modifier
                  .fillMaxWidth()
                  .clip(RoundedCornerShape(10.dp))
                  .background(MaterialTheme.colorScheme.surfaceContainerLow)
                  .padding(10.dp)
              ) {
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                  Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                  ) {
                    Text("INBOUND RETURN WINDOW", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.outline)
                    Text("3 Days Later", fontSize = 10.sp, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.primary)
                  }
                  Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                  ) {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                      Icon(Icons.Outlined.EventRepeat, contentDescription = null, tint = MaterialTheme.colorScheme.outline, modifier = Modifier.size(16.dp))
                      Text(flightForm.inboundDate, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                    }
                    Box(
                      modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(MaterialTheme.colorScheme.surfaceContainerLowest)
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                      Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                        Icon(Icons.Filled.Bedtime, contentDescription = null, tint = MaterialTheme.colorScheme.secondary, modifier = Modifier.size(14.dp))
                        Text(flightForm.inboundTimeWindow, fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
                      }
                    }
                  }
                }
              }
            }

            // Preferred Carrier Grid
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
              Text("PREFERRED AIRLINE CARRIER", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.outline)
              val carriers = PreferredCarrier.entries
              Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                listOf(carriers[0], carriers[1]).forEach { carrier ->
                  val isSelected = flightForm.carrier == carrier
                  Box(
                    modifier = Modifier
                      .weight(1f)
                      .clip(RoundedCornerShape(10.dp))
                      .background(if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceContainerLow)
                      .clickable { repository.updateCarrier(carrier) }
                      .padding(10.dp),
                    contentAlignment = Alignment.CenterStart
                  ) {
                    Row(
                      modifier = Modifier.fillMaxWidth(),
                      horizontalArrangement = Arrangement.SpaceBetween,
                      verticalAlignment = Alignment.CenterVertically
                    ) {
                      Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        Icon(
                          if (isSelected) Icons.Filled.CheckCircle else Icons.Outlined.Circle,
                          contentDescription = null,
                          tint = if (isSelected) Color.White else MaterialTheme.colorScheme.outline,
                          modifier = Modifier.size(16.dp)
                        )
                        Text(
                          carrier.airlineName,
                          fontSize = 12.sp,
                          fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                          color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface
                        )
                      }
                      if (carrier.isPreferred) {
                        Box(
                          modifier = Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .background(PrimaryContainer)
                            .padding(horizontal = 4.dp, vertical = 1.dp)
                        ) {
                          Text("Preferred", fontSize = 9.sp, color = OnPrimaryContainer, fontWeight = FontWeight.Bold)
                        }
                      }
                    }
                  }
                }
              }

              Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                listOf(carriers[2], carriers[3]).forEach { carrier ->
                  val isSelected = flightForm.carrier == carrier
                  Box(
                    modifier = Modifier
                      .weight(1f)
                      .clip(RoundedCornerShape(10.dp))
                      .background(if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceContainerLow)
                      .clickable { repository.updateCarrier(carrier) }
                      .padding(10.dp),
                    contentAlignment = Alignment.CenterStart
                  ) {
                    Row(
                      modifier = Modifier.fillMaxWidth(),
                      horizontalArrangement = Arrangement.SpaceBetween,
                      verticalAlignment = Alignment.CenterVertically
                    ) {
                      Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        Icon(
                          if (isSelected) Icons.Filled.CheckCircle else Icons.Outlined.Circle,
                          contentDescription = null,
                          tint = if (isSelected) Color.White else MaterialTheme.colorScheme.outline,
                          modifier = Modifier.size(16.dp)
                        )
                        Text(
                          carrier.airlineName,
                          fontSize = 12.sp,
                          fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                          color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface
                        )
                      }
                    }
                  }
                }
              }
            }

            // Frequent Flyer Box
            Box(
              modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp))
                .background(MaterialTheme.colorScheme.surfaceContainerLow)
                .padding(10.dp)
            ) {
              Column {
                Text("AIRLINE LOYALTY / FREQUENT FLYER (OPTIONAL)", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.outline)
                Row(
                  verticalAlignment = Alignment.CenterVertically,
                  horizontalArrangement = Arrangement.spacedBy(6.dp),
                  modifier = Modifier.padding(top = 4.dp)
                ) {
                  Icon(Icons.Filled.CardMembership, contentDescription = null, tint = MaterialTheme.colorScheme.outline, modifier = Modifier.size(18.dp))
                  Text(flightForm.frequentFlyer, fontSize = 13.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace)
                }
              }
            }

            // Seat Preference
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
              Text("SEAT ASSIGNMENT PREFERENCE", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.outline)
              Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                SeatPreference.entries.forEach { seat ->
                  val isSelected = flightForm.seatPreference == seat
                  Box(
                    modifier = Modifier
                      .weight(1f)
                      .clip(RoundedCornerShape(8.dp))
                      .background(if (isSelected) MaterialTheme.colorScheme.surfaceContainerHighest else MaterialTheme.colorScheme.surfaceContainerLow)
                      .clickable { repository.updateSeatPreference(seat) }
                      .padding(vertical = 8.dp, horizontal = 6.dp),
                    contentAlignment = Alignment.Center
                  ) {
                    Row(
                      verticalAlignment = Alignment.CenterVertically,
                      horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                      if (seat == SeatPreference.WINDOW) {
                        Icon(Icons.Filled.AirlineSeatReclineExtra, contentDescription = null, tint = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline, modifier = Modifier.size(15.dp))
                      } else if (seat == SeatPreference.AISLE) {
                        Icon(Icons.Filled.AirlineSeatReclineNormal, contentDescription = null, tint = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline, modifier = Modifier.size(15.dp))
                      }
                      Text(
                        seat.label,
                        fontSize = 11.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                        color = if (isSelected) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.onSurfaceVariant
                      )
                    }
                  }
                }
              }
            }

            // Complimentary Meal Category
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
              Text("COMPLIMENTARY MEAL CATEGORY", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.outline)
              val meals = MealPreference.entries
              Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                listOf(meals[0], meals[1]).forEach { meal ->
                  val isSelected = flightForm.mealPreference == meal
                  Box(
                    modifier = Modifier
                      .weight(1f)
                      .clip(RoundedCornerShape(8.dp))
                      .background(if (isSelected) MaterialTheme.colorScheme.surfaceContainerHighest else MaterialTheme.colorScheme.surfaceContainerLow)
                      .clickable { repository.updateMealPreference(meal) }
                      .padding(8.dp)
                  ) {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                      Box(
                        modifier = Modifier
                          .size(8.dp)
                          .clip(CircleShape)
                          .background(if (meal == MealPreference.VEG_HINDU) EmeraldGreen else ErrorRed)
                      )
                      Text(
                        meal.label,
                        fontSize = 11.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                        color = if (isSelected) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.onSurfaceVariant
                      )
                    }
                  }
                }
              }

              Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                listOf(meals[2], meals[3]).forEach { meal ->
                  val isSelected = flightForm.mealPreference == meal
                  Box(
                    modifier = Modifier
                      .weight(1f)
                      .clip(RoundedCornerShape(8.dp))
                      .background(if (isSelected) MaterialTheme.colorScheme.surfaceContainerHighest else MaterialTheme.colorScheme.surfaceContainerLow)
                      .clickable { repository.updateMealPreference(meal) }
                      .padding(8.dp)
                  ) {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                      Box(
                        modifier = Modifier
                          .size(8.dp)
                          .clip(CircleShape)
                          .background(if (meal == MealPreference.DIABETIC) Color(0xFF2563EB) else Color(0xFFD97706))
                      )
                      Text(
                        meal.label,
                        fontSize = 11.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                        color = if (isSelected) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.onSurfaceVariant
                      )
                    }
                  }
                }
              }
            }
          }
        }
      }
    }

    // 4. Hotel Accommodation Section (Shown if Hotel or Both)
    if (selectedReq != TripRequirement.FLIGHTS_ONLY) {
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
            // Header
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
              ) {
                Box(
                  modifier = Modifier
                    .size(34.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(PrimaryFixed),
                  contentAlignment = Alignment.Center
                ) {
                  Icon(Icons.Filled.Apartment, contentDescription = null, tint = OnPrimaryFixed, modifier = Modifier.size(18.dp))
                }
                Column {
                  Text("Hotel Accommodation", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                  Text("Tier-1 Corporate Partner Lodging", fontSize = 10.sp, color = MaterialTheme.colorScheme.outline)
                }
              }

              Box(
                modifier = Modifier
                  .clip(RoundedCornerShape(4.dp))
                  .background(MaterialTheme.colorScheme.surfaceContainerHigh)
                  .padding(horizontal = 6.dp, vertical = 2.dp)
              ) {
                Text("BAND B ELIGIBLE", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurfaceVariant)
              }
            }

            // Target City & Hotel Name
            Box(
              modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp))
                .background(MaterialTheme.colorScheme.surfaceContainerLow)
                .padding(10.dp)
            ) {
              Column {
                Text("TARGET CITY & HOTEL NAME", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.outline)
                Row(
                  verticalAlignment = Alignment.CenterVertically,
                  horizontalArrangement = Arrangement.spacedBy(6.dp),
                  modifier = Modifier.padding(top = 4.dp)
                ) {
                  Icon(Icons.Filled.LocationOn, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(18.dp))
                  Text(hotelForm.propertyName, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                }
                Text(
                  "Or nearest corporate-discounted hotel within 5km radius",
                  fontSize = 10.sp,
                  color = MaterialTheme.colorScheme.outline,
                  fontStyle = androidx.compose.ui.text.font.FontStyle.Italic,
                  modifier = Modifier.padding(top = 2.dp)
                )
              }
            }

            // Stay Dates & Duration
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
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                  Icon(Icons.Filled.DateRange, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(20.dp))
                  Column {
                    Text("CHECK-IN / CHECK-OUT", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.outline)
                    Text(hotelForm.stayDates, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                  }
                }
                Box(
                  modifier = Modifier
                    .clip(RoundedCornerShape(100.dp))
                    .background(MaterialTheme.colorScheme.surfaceContainerHighest)
                    .padding(horizontal = 10.dp, vertical = 4.dp)
                ) {
                  Text("${hotelForm.nights} Nights", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                }
              }
            }

            // Room Category
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
              Text("ROOM CATEGORY (ENTITLEMENT)", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.outline)
              Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                RoomCategory.entries.forEach { room ->
                  val isSelected = hotelForm.roomCategory == room
                  Box(
                    modifier = Modifier
                      .weight(1f)
                      .clip(RoundedCornerShape(10.dp))
                      .background(if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceContainerLow)
                      .clickable { repository.updateRoomCategory(room) }
                      .padding(10.dp)
                  ) {
                    Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                      Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                      ) {
                        Text(
                          room.label,
                          fontSize = 13.sp,
                          fontWeight = FontWeight.Bold,
                          color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface
                        )
                        Icon(
                          if (isSelected) Icons.Filled.CheckCircle else Icons.Outlined.Circle,
                          contentDescription = null,
                          tint = if (isSelected) Color.White else MaterialTheme.colorScheme.outline,
                          modifier = Modifier.size(16.dp)
                        )
                      }
                      Text(
                        room.subtitle,
                        fontSize = 10.sp,
                        color = if (isSelected) Color.White.copy(alpha = 0.85f) else MaterialTheme.colorScheme.outline
                      )
                    }
                  }
                }
              }
            }

            // Rate Cancellation Policy
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
              Text("RATE CANCELLATION POLICY", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.outline)
              Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                RatePolicy.entries.forEach { policy ->
                  val isSelected = hotelForm.ratePolicy == policy
                  Box(
                    modifier = Modifier
                      .weight(1f)
                      .clip(RoundedCornerShape(10.dp))
                      .background(MaterialTheme.colorScheme.surfaceContainerLow)
                      .clickable { repository.updateRatePolicy(policy) }
                      .padding(10.dp)
                  ) {
                    Row(
                      verticalAlignment = Alignment.CenterVertically,
                      horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                      RadioButton(
                        selected = isSelected,
                        onClick = { repository.updateRatePolicy(policy) },
                        colors = RadioButtonDefaults.colors(selectedColor = MaterialTheme.colorScheme.primary)
                      )
                      Column {
                        Text(policy.label, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        Text(policy.subtitle, fontSize = 10.sp, color = MaterialTheme.colorScheme.outline)
                      }
                    }
                  }
                }
              }
            }

            // Hotel Rewards
            Box(
              modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp))
                .background(MaterialTheme.colorScheme.surfaceContainerLow)
                .padding(10.dp)
            ) {
              Column {
                Text("HOTEL REWARDS / BONVOY / HILTON #", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.outline)
                Row(
                  verticalAlignment = Alignment.CenterVertically,
                  horizontalArrangement = Arrangement.spacedBy(6.dp),
                  modifier = Modifier.padding(top = 4.dp)
                ) {
                  Icon(Icons.Filled.HotelClass, contentDescription = null, tint = MaterialTheme.colorScheme.outline, modifier = Modifier.size(18.dp))
                  Text(hotelForm.rewardsNumber, fontSize = 13.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace)
                }
              }
            }

            // Early Check-In Checkbox
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
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                  Icon(Icons.Filled.MoreTime, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(20.dp))
                  Column {
                    Text("Request Early Check-in (10:00 AM)", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    Text("Subject to availability upon arrival", fontSize = 10.sp, color = MaterialTheme.colorScheme.outline)
                  }
                }
                Checkbox(
                  checked = hotelForm.earlyCheckIn,
                  onCheckedChange = { repository.updateEarlyCheckIn(it) },
                  colors = CheckboxDefaults.colors(checkedColor = MaterialTheme.colorScheme.primary)
                )
              }
            }

            // Special Instructions
            Box(
              modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp))
                .background(MaterialTheme.colorScheme.surfaceContainerLow)
                .padding(10.dp)
            ) {
              Column {
                Text("SPECIAL INSTRUCTIONS FOR DESK", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.outline)
                Text(
                  hotelForm.specialInstructions,
                  fontSize = 12.sp,
                  color = MaterialTheme.colorScheme.onSurface,
                  modifier = Modifier.padding(top = 4.dp)
                )
              }
            }
          }
        }
      }
    }

    // 5. MSI Travel Policy Notice Banner
    item {
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .clip(RoundedCornerShape(16.dp))
          .background(AmberNoticeLight)
          .padding(14.dp)
      ) {
        Row(
          verticalAlignment = Alignment.Top,
          horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
          Icon(
            Icons.Filled.VerifiedUser,
            contentDescription = null,
            tint = AmberNotice,
            modifier = Modifier.size(24.dp)
          )
          Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(
              text = "MSI Travel Policy Notice",
              fontWeight = FontWeight.Bold,
              fontSize = 13.sp,
              color = AmberText
            )
            Text(
              text = "If estimated itinerary exceeds the departmental threshold, this request will automatically ping Arun Patel (Reporting Manager) for 1-click email authorization before fulfillment.",
              fontSize = 11.sp,
              color = MaterialTheme.colorScheme.onSurfaceVariant,
              lineHeight = 16.sp
            )
            Row(
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.spacedBy(4.dp),
              modifier = Modifier.padding(top = 2.dp)
            ) {
              Icon(Icons.Filled.HistoryEdu, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(13.dp))
              Text(
                text = "SLA Turnaround: Tickets dispatched within 4 operational hours",
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
              )
            }
          }
        }
      }
    }

    // 6. Sticky-Style Action Tray & Save Options
    item {
      Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Button(
          onClick = { repository.submitRequest() },
          modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .testTag("submit_travel_request_btn"),
          shape = RoundedCornerShape(12.dp),
          colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
        ) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
          ) {
            Text(
              text = "Submit Travel Request",
              fontWeight = FontWeight.Bold,
              fontSize = 15.sp
            )
            Icon(Icons.Default.ArrowForward, contentDescription = null, modifier = Modifier.size(18.dp))
          }
        }

        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier
              .clickable { repository.saveDraft() }
              .testTag("save_draft_btn")
          ) {
            Icon(Icons.Filled.Save, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(16.dp))
            Text("Save as Draft", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
          }

          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
          ) {
            Icon(Icons.Filled.Shield, contentDescription = null, tint = EmeraldGreen, modifier = Modifier.size(14.dp))
            Text("Desk Super Admin: Idrish Khan", fontSize = 10.sp, color = MaterialTheme.colorScheme.outline)
          }
        }
      }
    }
  }
}
