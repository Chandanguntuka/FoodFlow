import React, { useState } from 'react'
import { Clock, IndianRupee, MapPin, Pencil, Save, Star, Trash2, X } from 'lucide-react'
import { useNavigate } from 'react-router-dom'
import toast from 'react-hot-toast'
import { restaurantAPI } from '../services/api'

export default function RestaurantCard({ restaurant, canManage = false, onChange, onDelete }) {
  const navigate = useNavigate()
  const [editing, setEditing] = useState(false)
  const [saving, setSaving] = useState(false)
  const [form, setForm] = useState({
    name: restaurant.name || '',
    cuisine: restaurant.cuisine || '',
    location: restaurant.location || '',
    imageUrl: restaurant.imageUrl || '',
    rating: restaurant.rating || 4.2,
    deliveryTime: restaurant.deliveryTime || '30-40 min',
    minOrder: restaurant.minOrder || 149,
    isOpen: restaurant.isOpen ?? true,
  })

  const save = async () => {
    try {
      setSaving(true)
      const res = await restaurantAPI.update(restaurant.id, form)
      onChange?.(res.data)
      setEditing(false)
      toast.success('Restaurant updated')
    } catch (err) {
      toast.error(err.response?.data?.message || 'Could not update restaurant')
    } finally {
      setSaving(false)
    }
  }

  const remove = async () => {
    if (!window.confirm(`Delete ${restaurant.name}?`)) return
    try {
      await restaurantAPI.delete(restaurant.id)
      onDelete?.(restaurant.id)
      toast.success('Restaurant deleted')
    } catch (err) {
      toast.error(err.response?.data?.message || 'Could not delete restaurant')
    }
  }

  if (editing) {
    return (
      <div className="overflow-hidden rounded-lg border border-primary-200 bg-white shadow-sm">
        <div className="space-y-3 p-4">
          <input className="input-field" value={form.name} onChange={e => setForm(data => ({ ...data, name: e.target.value }))} placeholder="Restaurant name" />
          <input className="input-field" value={form.cuisine} onChange={e => setForm(data => ({ ...data, cuisine: e.target.value }))} placeholder="Cuisine" />
          <input className="input-field" value={form.location} onChange={e => setForm(data => ({ ...data, location: e.target.value }))} placeholder="Location" />
          <input className="input-field" value={form.imageUrl} onChange={e => setForm(data => ({ ...data, imageUrl: e.target.value }))} placeholder="Image URL" />
          <div className="grid grid-cols-3 gap-2">
            <input className="input-field" type="number" step="0.1" value={form.rating} onChange={e => setForm(data => ({ ...data, rating: e.target.value }))} placeholder="Rating" />
            <input className="input-field" value={form.deliveryTime} onChange={e => setForm(data => ({ ...data, deliveryTime: e.target.value }))} placeholder="Delivery" />
            <input className="input-field" type="number" value={form.minOrder} onChange={e => setForm(data => ({ ...data, minOrder: e.target.value }))} placeholder="Min order" />
          </div>
          <div className="grid grid-cols-2 gap-2">
            <button type="button" onClick={save} disabled={saving} className="btn-primary flex items-center justify-center gap-2 py-2.5 text-sm">
              <Save className="h-4 w-4" /> {saving ? 'Saving...' : 'Save'}
            </button>
            <button type="button" onClick={() => setEditing(false)} className="btn-secondary flex items-center justify-center gap-2 py-2.5 text-sm">
              <X className="h-4 w-4" /> Cancel
            </button>
          </div>
        </div>
      </div>
    )
  }

  return (
    <div className="overflow-hidden rounded-lg border border-gray-200 bg-white shadow-sm transition hover:-translate-y-1 hover:shadow-md">
      <button type="button" onClick={() => navigate(`/restaurant/${restaurant.id}`)} className="block w-full text-left">
        <img src={restaurant.imageUrl} alt={restaurant.name} className="h-44 w-full object-cover" />
      </button>
      <div className="space-y-3 p-4">
        <div className="flex items-start justify-between gap-3">
          <button type="button" onClick={() => navigate(`/restaurant/${restaurant.id}`)} className="min-w-0 text-left">
            <h3 className="font-bold text-gray-950">{restaurant.name}</h3>
            <p className="text-sm text-gray-500">{restaurant.cuisine}</p>
          </button>
          <span className="rounded-md bg-green-600 px-2 py-1 text-xs font-bold text-white">Star {restaurant.rating}</span>
        </div>
        <div className="grid grid-cols-2 gap-2 text-xs text-gray-600">
          <span className="flex items-center gap-1"><MapPin className="h-3.5 w-3.5" />{restaurant.location}</span>
          <span className="flex items-center gap-1"><Clock className="h-3.5 w-3.5" />{restaurant.deliveryTime}</span>
          <span className="flex items-center gap-1"><IndianRupee className="h-3.5 w-3.5" />Min {restaurant.minOrder}</span>
          <span className="flex items-center gap-1"><Star className="h-3.5 w-3.5 fill-yellow-400 text-yellow-400" />Open now</span>
        </div>
        {canManage && (
          <div className="grid grid-cols-2 gap-2 border-t border-gray-100 pt-3">
            <button type="button" onClick={() => setEditing(true)} className="flex items-center justify-center gap-2 rounded-md border border-gray-200 px-3 py-2 text-sm font-bold text-gray-700 hover:border-primary-300 hover:text-primary-600">
              <Pencil className="h-4 w-4" /> Edit
            </button>
            <button type="button" onClick={remove} className="flex items-center justify-center gap-2 rounded-md border border-red-200 px-3 py-2 text-sm font-bold text-red-600 hover:bg-red-50">
              <Trash2 className="h-4 w-4" /> Delete
            </button>
          </div>
        )}
      </div>
    </div>
  )
}
