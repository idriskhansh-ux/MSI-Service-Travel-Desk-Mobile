package com.example.model

enum class TripRequirement(val label: String) {
  FLIGHTS_ONLY("Flights Only"),
  HOTEL_ONLY("Hotel Only"),
  BOTH("Flight + Hotel")
}

enum class TripType(val label: String) {
  ROUND_TRIP("Round Trip"),
  ONE_WAY("One-Way"),
  MULTI_CITY("Multi-City")
}

enum class PreferredCarrier(val code: String, val airlineName: String, val isPreferred: Boolean = false) {
  INDIGO("6E", "IndiGo (6E)", true),
  AIR_INDIA("AI", "Air India (AI)", false),
  VISTARA("UK", "Vistara (UK)", false),
  ANY("", "Any Suitable", false)
}

enum class SeatPreference(val label: String) {
  WINDOW("Window"),
  AISLE("Aisle"),
  NO_PREFERENCE("No Preference")
}

enum class MealPreference(val code: String, val label: String) {
  VEG_HINDU("AVML", "Veg Hindu Meal (AVML)"),
  NON_VEG("NVML", "Non-Veg (NVML)"),
  DIABETIC("DBML", "Diabetic Meal (DBML)"),
  JAIN("VJML", "Jain Meal (VJML)")
}

enum class RoomCategory(val label: String, val subtitle: String) {
  STANDARD_KING("Standard King", "Company Policy Standard"),
  EXECUTIVE_CLUB("Executive Club", "Requires VP Approval")
}

enum class RatePolicy(val label: String, val subtitle: String) {
  FLEXIBLE("Flexible Rate", "Free Cancellation"),
  LOWEST("Lowest Fare", "Non-refundable")
}

enum class DevicePlatform {
  IOS,
  ANDROID
}

data class FlightItinerary(
  val origin: String = "BLR - Kempegowda Intl, Bengaluru",
  val originCode: String = "BLR",
  val destination: String = "DEL - Indira Gandhi Intl, New Delhi",
  val destinationCode: String = "DEL",
  val outboundDate: String = "18 Nov 2024",
  val outboundTimeWindow: String = "Morning (06:00 - 10:00)",
  val inboundDate: String = "21 Nov 2024",
  val inboundTimeWindow: String = "Evening (18:00 - 22:00)",
  val carrier: PreferredCarrier = PreferredCarrier.INDIGO,
  val frequentFlyer: String = "6E-REWARDS-49821",
  val seatPreference: SeatPreference = SeatPreference.WINDOW,
  val mealPreference: MealPreference = MealPreference.VEG_HINDU,
  val pnr: String = "6E-88392K",
  val flightNumber: String = "6E 502"
)

data class HotelItinerary(
  val propertyName: String = "JW Marriott Aerocity, New Delhi",
  val stayDates: String = "18 Nov 2024 - 21 Nov 2024",
  val nights: Int = 3,
  val roomCategory: RoomCategory = RoomCategory.STANDARD_KING,
  val ratePolicy: RatePolicy = RatePolicy.FLEXIBLE,
  val rewardsNumber: String = "Marriott Bonvoy #74129853",
  val earlyCheckIn: Boolean = true,
  val specialInstructions: String = "Quiet high-floor room preferred, late flight arrival"
)

data class ActiveMission(
  val originCode: String = "SFO",
  val destCode: String = "ORD",
  val originCity: String = "San Francisco",
  val destCity: String = "Chicago O'Hare",
  val dates: String = "Oct 24 - Oct 27, 2024",
  val duration: String = "4 Days Mission",
  val purpose: String = "Q4 Client Milestone & Enterprise Data Modernization Infrastructure Handover.",
  val settledCostUsd: Int = 1235,
  val policyCapUsd: Int = 1340,
  val savingsUsd: Int = 105,
  val flightVoucher: ConfirmedFlightVoucher = ConfirmedFlightVoucher(),
  val hotelVoucher: ConfirmedHotelVoucher = ConfirmedHotelVoucher(),
  val uberCode: String = "MSI-CHI-7749",
  val uberAllowance: String = "$50 Allowance Allocated for ORD Transfers"
)

data class ConfirmedFlightVoucher(
  val carrierName: String = "Delta Air Lines",
  val carrierCode: String = "DL",
  val flightNumber: String = "Flight DL 2940",
  val flightClass: String = "Corporate Economy Flex",
  val pnr: String = "DL-X8921K",
  val originCode: String = "SFO",
  val originTerminal: String = "Terminal 2 • Gate 42B",
  val originTime: String = "08:30 AM PST",
  val destCode: String = "ORD",
  val destTerminal: String = "Terminal 3",
  val destTime: String = "02:45 PM CST",
  val duration: String = "4h 15m (Non-stop)",
  val seat: String = "Seat 14B (Aisle)",
  val fileName: String = "DL-2940_E-Ticket_RahulSharma.pdf",
  val fileSize: String = "1.2 MB • Digitally Signed by MSI Desk"
)

data class ConfirmedHotelVoucher(
  val propertyName: String = "Chicago Marriott Downtown",
  val location: String = "Magnificent Mile • 540 N Michigan Ave",
  val confirmationNumber: String = "MAR-9941203",
  val roomDescription: String = "Confirmed High-Floor Deluxe",
  val checkIn: String = "Oct 24 (03:00 PM)",
  val checkOut: String = "Oct 27 (11:00 AM)",
  val roomPreference: String = "1 King Bed, Deluxe Room • High Floor Non-Smoking",
  val rateText: String = "$165 USD / night",
  val rateCapText: String = "(Under Cap: $180/night) • Taxes Included",
  val fileName: String = "Marriott_ORD_Res78923.pdf",
  val fileSize: String = "840 KB • Guarantee: Direct MSI Bill"
)

data class PastTrip(
  val route: String,
  val dates: String,
  val description: String,
  val vouchersCount: Int = 2,
  val zipFileName: String
)

data class UserProfileData(
  val name: String = "Idrish Khan",
  val role: String = "Senior Associate & Super Admin",
  val department: String = "MSI Services • Corporate Travel Desk",
  val empId: String = "EMP #MSI-8841",
  val costCenter: String = "CC-4010 Enterprise Architecture",
  val email: String = "idrish.khan@msisurfaces.com",
  val phone: String = "+91 98451 22345",
  val tripsTaken: Int = 24,
  val tripsTakenDetail: String = "18 Intl • 6 Dom",
  val activeBookings: Int = 3,
  val activeBookingsDetail: String = "2 Confirmed • 1 Queue",
  val spendManaged: String = "$515K",
  val spendDetail: String = "YTD Audited",
  val entitlementBand: String = "Band A (Executive Entitlement)",
  val entitlementDetail: String = "Intl Business Class • 5★ Luxury Accommodations • Chauffeur Concierge",
  val bypassPrivileges: Boolean = true,
  val managerName: String = "Arun Patel",
  val managerRole: String = "Head of Engineering • MSI Surfaces",
  val managerEmail: String = "arun.patel@msisurfaces.com",
  val managerPhone: String = "+91 98200 11223",
  val preferredAirlines: List<String> = listOf("IndiGo (6E)", "Air India (AI)", "Vistara (UK)"),
  val frequentFlyerAccounts: List<Pair<String, String>> = listOf(
    "IndiGo 6E Rewards" to "6E-REWARDS-49821",
    "Air India Flying Returns" to "AI-894102"
  ),
  val hotelLoyalties: List<Pair<String, String>> = listOf(
    "Marriott Bonvoy (Titanium)" to "#74129853",
    "Taj Epicure Club" to "#98321"
  ),
  val defaultRoomTags: List<String> = listOf("High Floor", "Non-Smoking", "King Bed", "Early Check-In Priority"),
  val mirrorQueueActive: Boolean = true
)
