package com.example.myapplication.model

import android.os.Parcel
import android.os.Parcelable

data class CartModel(
    val product: ProductModel,
    var quantity: Int,
    var total: Float
    ) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readParcelable(ProductModel::class.java.classLoader)!!,
        parcel.readInt(),
        parcel.readFloat()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(product, flags)
        parcel.writeInt(quantity)
        parcel.writeFloat(total)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CartModel> {
        override fun createFromParcel(parcel: Parcel): CartModel {
            return CartModel(parcel)
        }

        override fun newArray(size: Int): Array<CartModel?> {
            return arrayOfNulls(size)
        }
    }
}
