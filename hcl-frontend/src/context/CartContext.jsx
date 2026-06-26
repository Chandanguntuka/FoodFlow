import React, { createContext, useContext, useEffect, useMemo, useState } from 'react'
import toast from 'react-hot-toast'

const CartContext = createContext(null)
const DELIVERY_CHARGE = 40

export function CartProvider({ children }) {
  const [items, setItems] = useState(() => JSON.parse(localStorage.getItem('cartItems') || '[]'))
  const [restaurant, setRestaurant] = useState(() => JSON.parse(localStorage.getItem('cartRestaurant') || 'null'))

  useEffect(() => localStorage.setItem('cartItems', JSON.stringify(items)), [items])
  useEffect(() => localStorage.setItem('cartRestaurant', JSON.stringify(restaurant)), [restaurant])

  const addItem = (item, sourceRestaurant) => {
    if (restaurant?.id && restaurant.id !== sourceRestaurant.id) {
      setItems([{ ...item, quantity: 1 }])
      setRestaurant(sourceRestaurant)
      toast.success('Cart switched to this restaurant')
      return
    }
    setRestaurant(sourceRestaurant)
    setItems((current) => {
      const existing = current.find((line) => line.id === item.id)
      if (existing) return current.map((line) => line.id === item.id ? { ...line, quantity: line.quantity + 1 } : line)
      return [...current, { ...item, quantity: 1 }]
    })
    toast.success('Added to cart')
  }

  const updateQty = (id, quantity) => {
    if (quantity <= 0) {
      removeItem(id)
      return
    }
    setItems((current) => current.map((line) => line.id === id ? { ...line, quantity } : line))
  }

  const removeItem = (id) => setItems((current) => current.filter((line) => line.id !== id))

  const clearCart = () => {
    setItems([])
    setRestaurant(null)
  }

  const subtotal = useMemo(() => items.reduce((sum, line) => sum + Number(line.price) * line.quantity, 0), [items])
  const total = items.length ? subtotal + DELIVERY_CHARGE : 0
  const cartCount = items.reduce((sum, line) => sum + line.quantity, 0)

  return (
    <CartContext.Provider value={{ items, restaurant, cartCount, subtotal, deliveryCharge: DELIVERY_CHARGE, total, addItem, updateQty, removeItem, clearCart }}>
      {children}
    </CartContext.Provider>
  )
}

export const useCart = () => {
  const context = useContext(CartContext)
  if (!context) throw new Error('useCart must be used within CartProvider')
  return context
}
