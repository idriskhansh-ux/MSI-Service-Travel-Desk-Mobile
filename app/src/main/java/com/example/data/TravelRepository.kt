package com.example.data

import com.example.model.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class TravelRepository {
  private val _isAuthenticated = MutableStateFlow(true)
  val isAuthenticated: StateFlow<Boolean> = _isAuthenticated.asStateFlow()

  private val _userProfile = MutableStateFlow(UserProfileData())
  val userProfile: StateFlow<UserProfileData> = _userProfile.asStateFlow()

  private val _activeMission = MutableStateFlow(ActiveMission())
  val activeMission: StateFlow<ActiveMission> = _activeMission.asStateFlow()

  private val _devicePlatform = MutableStateFlow(DevicePlatform.IOS)
  val devicePlatform: StateFlow<DevicePlatform> = _devicePlatform.asStateFlow()

  private val _selectedRequirement = MutableStateFlow(TripRequirement.BOTH)
  val selectedRequirement: StateFlow<TripRequirement> = _selectedRequirement.asStateFlow()

  private val _selectedTripType = MutableStateFlow(TripType.ROUND_TRIP)
  val selectedTripType: StateFlow<TripType> = _selectedTripType.asStateFlow()

  private val _flightForm = MutableStateFlow(FlightItinerary())
  val flightForm: StateFlow<FlightItinerary> = _flightForm.asStateFlow()

  private val _hotelForm = MutableStateFlow(HotelItinerary())
  val hotelForm: StateFlow<HotelItinerary> = _hotelForm.asStateFlow()

  private val _pastTrips = MutableStateFlow(
    listOf(
      PastTrip(
        route = "NYC ⇄ ATL",
        dates = "Sep 14 - Sep 18",
        description = "Delta Air Lines & Hilton Atlanta",
        vouchersCount = 2,
        zipFileName = "NYC-ATL-2024.zip"
      ),
      PastTrip(
        route = "DFW ⇄ SEA",
        dates = "Aug 02 - Aug 05",
        description = "American Airlines & Westin Seattle",
        vouchersCount = 2,
        zipFileName = "DFW-SEA-2024.zip"
      )
    )
  )
  val pastTrips: StateFlow<List<PastTrip>> = _pastTrips.asStateFlow()

  private val _toastMessage = MutableStateFlow<String?>(null)
  val toastMessage: StateFlow<String?> = _toastMessage.asStateFlow()

  private val _isWorkdaySyncing = MutableStateFlow(false)
  val isWorkdaySyncing: StateFlow<Boolean> = _isWorkdaySyncing.asStateFlow()

  fun setRequirement(req: TripRequirement) {
    _selectedRequirement.value = req
  }

  fun setTripType(type: TripType) {
    _selectedTripType.value = type
  }

  fun setDevicePlatform(platform: DevicePlatform) {
    _devicePlatform.value = platform
  }

  fun swapAirports() {
    _flightForm.update { current ->
      val newOrigin = current.destination
      val newOriginCode = current.destinationCode
      val newDest = current.origin
      val newDestCode = current.originCode
      current.copy(
        origin = newOrigin,
        originCode = newOriginCode,
        destination = newDest,
        destinationCode = newDestCode
      )
    }
  }

  fun updateFlightOrigin(origin: String, code: String) {
    _flightForm.update { it.copy(origin = origin, originCode = code) }
  }

  fun updateFlightDest(dest: String, code: String) {
    _flightForm.update { it.copy(destination = dest, destinationCode = code) }
  }

  fun updateCarrier(carrier: PreferredCarrier) {
    _flightForm.update { it.copy(carrier = carrier) }
  }

  fun updateFrequentFlyer(number: String) {
    _flightForm.update { it.copy(frequentFlyer = number) }
  }

  fun updateSeatPreference(seat: SeatPreference) {
    _flightForm.update { it.copy(seatPreference = seat) }
  }

  fun updateMealPreference(meal: MealPreference) {
    _flightForm.update { it.copy(mealPreference = meal) }
  }

  fun updateHotelProperty(property: String) {
    _hotelForm.update { it.copy(propertyName = property) }
  }

  fun updateRoomCategory(category: RoomCategory) {
    _hotelForm.update { it.copy(roomCategory = category) }
  }

  fun updateRatePolicy(policy: RatePolicy) {
    _hotelForm.update { it.copy(ratePolicy = policy) }
  }

  fun updateHotelRewards(number: String) {
    _hotelForm.update { it.copy(rewardsNumber = number) }
  }

  fun updateEarlyCheckIn(checked: Boolean) {
    _hotelForm.update { it.copy(earlyCheckIn = checked) }
  }

  fun updateSpecialInstructions(instructions: String) {
    _hotelForm.update { it.copy(specialInstructions = instructions) }
  }

  fun toggleMirrorQueue() {
    _userProfile.update { it.copy(mirrorQueueActive = !it.mirrorQueueActive) }
  }

  fun submitRequest(): String {
    val reqType = _selectedRequirement.value.label
    val origin = _flightForm.value.originCode
    val dest = _flightForm.value.destinationCode
    val confirmation = "Request Successfully Dispatched! ✈️ ($reqType $origin ⇄ $dest). Group_MSI.TravelDesk & Arun Patel notified."
    showToast(confirmation)
    return confirmation
  }

  fun saveDraft() {
    showToast("Draft Saved Locally: Itinerary cached for Idrish Khan.")
  }

  fun syncWorkday() {
    _isWorkdaySyncing.value = true
    showToast("Triggering Workday & SAP HRMS Sync...")
  }

  fun finishWorkdaySync() {
    _isWorkdaySyncing.value = false
    showToast("Workday & SAP HRMS synchronized successfully.")
  }

  fun login(email: String) {
    _isAuthenticated.value = true
    showToast("Authenticated as $email")
  }

  fun logout() {
    _isAuthenticated.value = false
    showToast("Logged out of MSI Corporate SSO")
  }

  fun showToast(msg: String) {
    _toastMessage.value = msg
  }

  fun clearToast() {
    _toastMessage.value = null
  }
}
