package cmps312.lab.bankingapp.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


class Transfer() {
    var fromAccountNo: String? = null
    var beneficiaryName: String? = null
    var beneficiaryAccountNo: String? = null
    var amount: Double = 0.0
}