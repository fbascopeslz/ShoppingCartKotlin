package com.example.myapplication.adapter

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ActivityCartBinding
import com.example.myapplication.databinding.LayoutCartItemBinding
import com.example.myapplication.model.CartModel
import com.squareup.picasso.Picasso

class CartAdapter : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {
    private lateinit var binding: LayoutCartItemBinding
    var cartList: ArrayList<CartModel> = ArrayList()
    lateinit var context: Context
    private lateinit var bindingCartAcivity: ActivityCartBinding

    fun CartAdapter(cartList : ArrayList<CartModel>, context: Context, bindingCartAcivity: ActivityCartBinding) {
        this.cartList = cartList
        this.context = context
        this.bindingCartAcivity = bindingCartAcivity
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        binding = LayoutCartItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bind(cartList[position])

        val cart = cartList.get(position)

        holder.itemBinding.btnMinus.setOnClickListener {
            minusCartItem(holder, cart)
        }
        holder.itemBinding.btnPlus.setOnClickListener {
            plusCartItem(holder, cart)
        }
        holder.itemBinding.btnDelete.setOnClickListener {
            val builder = AlertDialog.Builder(context)
            builder.setTitle("Eliminar producto")
                .setMessage("Desea eliminar este producto?")
                .setPositiveButton("OK",
                    DialogInterface.OnClickListener { dialog, id ->
                        deleteProductFromCart(cart, position)
                        dialog.dismiss()
                    })
                .setNegativeButton("Cancelar",
                    DialogInterface.OnClickListener { dialog, id ->
                        dialog.dismiss()
                    })
            builder.create().show()
        }
    }

    private fun deleteProductFromCart(cart: CartModel, position: Int) {
        cartList.remove(cart)
        notifyItemRemoved(position)
        updateTotalPriceCart()
    }

    private fun plusCartItem(holder: CartViewHolder, cart: CartModel) {
        cart.quantity = cart.quantity + 1
        cart.total = (cart.quantity * cart.product.price!!.toInt()).toFloat()

        holder.itemBinding.apply {
            txtQuantity.text = cart.quantity.toString()
            txtTotalPrice.text = "${(cart.quantity * cart.product.price!!.toInt()).toString()} Bs."
        }

        updateTotalPriceCart()
    }

    private fun minusCartItem(holder: CartViewHolder, cart: CartModel) {
        if (cart.quantity > 0) {
            cart.quantity = cart.quantity - 1
            cart.total = (cart.quantity * cart.product.price!!.toInt()).toFloat()

            holder.itemBinding.apply {
                txtQuantity.text = cart.quantity.toString()
                txtTotalPrice.text = "${(cart.quantity * cart.product.price!!.toInt()).toString()} Bs."
            }

            updateTotalPriceCart()
        }
    }

    private fun updateTotalPriceCart() {
        var total = 0f
        for (cart in cartList) {
            total += (cart.quantity * cart.product.price!!.toInt()).toFloat()
        }

        bindingCartAcivity.totalCart.text = "TOTAL: ${total.toInt().toString()} Bs."
    }

    override fun getItemCount(): Int {
        return cartList.size
    }

    inner class CartViewHolder(var itemBinding: LayoutCartItemBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(item : CartModel) {
            binding.apply {
                txtPrice.text = "${item.product.price} Bs."
                txtName.text = item.product.name
                txtQuantity.text = item.quantity.toString()
                txtTotalPrice.text = "${(item.quantity * item.product.price!!.toInt()).toString()} Bs."
                Picasso.get().load(item.product.image).into(imageView)
            }
            updateTotalPriceCart()
        }
    }
}