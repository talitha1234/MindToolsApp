import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PhoneCallViewModel : ViewModel() {
    private val _contactName = MutableStateFlow("")
    val contactName: StateFlow<String> = _contactName

    private val _phoneNumber = MutableStateFlow("")
    val phoneNumber: StateFlow<String> = _phoneNumber

    private val _isValid = MutableStateFlow(false)
    val isValid: StateFlow<Boolean> = _isValid

    fun onContactNameChanged(newName: String) {
        _contactName.value = newName
        validate()
    }

    fun onPhoneNumberChanged(newNumber: String) {
        _phoneNumber.value = newNumber
        validate()
    }

    private fun validate() {
        _isValid.value = _contactName.value.isNotBlank() && _phoneNumber.value.isNotBlank()
    }

    // You can add function to save or handle "onSave" here
    fun saveActivity(onSaved: (String, String) -> Unit) {
        if (_isValid.value) {
            onSaved(_contactName.value, _phoneNumber.value)
        }
    }
}
