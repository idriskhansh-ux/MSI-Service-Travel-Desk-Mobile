package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

  @Test
  fun `read string from context`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val appName = context.getString(R.string.app_name)
    assertEquals("MSI Travel Desk", appName)
  }

  @Test
  fun `verify travel repository initial state and airport swap`() {
    val repo = com.example.data.TravelRepository()
    assertEquals("BLR", repo.flightForm.value.originCode)
    assertEquals("DEL", repo.flightForm.value.destinationCode)

    repo.swapAirports()
    assertEquals("DEL", repo.flightForm.value.originCode)
    assertEquals("BLR", repo.flightForm.value.destinationCode)

    val confirmation = repo.submitRequest()
    assert(confirmation.contains("Request Successfully Dispatched"))
  }
}
