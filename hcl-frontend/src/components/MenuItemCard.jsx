import React, { useState } from 'react'
import { Pencil, Plus, Save, Star, Trash2, X } from 'lucide-react'
import toast from 'react-hot-toast'
import { useCart } from '../context/CartContext'
import { menuItemAPI } from '../services/api'

export default function MenuItemCard({ item, restaurant, canManage = false, onChange, onDelete }) {
  const { addItem } = useCart()
  const sourceRestaurant = restaurant || item.restaurant || { id: item.restaurantId, name: item.restaurantName || 'Restaurant' }
  const [editing, setEditing] = useState(false)
  const [saving, setSaving] = useState(false)
  const [form, setForm] = useState({
    name: item.name || '',
    description: item.description || '',
    price: item.price || 0,
    imageUrl: item.imageUrl || '',
    category: item.category || '',
    isVeg: item.isVeg ?? true,
    rating: item.rating || 4.2,
    preparationTime: item.preparationTime || 20,
    isPopular: item.isPopular || false,
    isAvailable: item.isAvailable ?? true,
  })

  const save = async () => {
    try {
      setSaving(true)
      const res = await menuItemAPI.update(item.id, form)
      onChange?.(res.data)
      setEditing(false)
      toast.success('Menu item updated')
    } catch (err) {
      toast.error(err.response?.data?.message || 'Could not update menu item')
    } finally {
      setSaving(false)
    }
  }

  const remove = async () => {
    if (!window.confirm(`Delete ${item.name}?`)) return
    try {
      await menuItemAPI.delete(item.id)
      onDelete?.(item.id)
      toast.success('Menu item deleted')
    } catch (err) {
      toast.error(err.response?.data?.message || 'Could not delete menu item')
    }
  }

  if (editing) {
    return (
      <div className="rounded-lg border border-primary-200 bg-white p-4 shadow-sm">
        <div className="space-y-3">
          <input className="input-field" value={form.name} onChange={e => setForm(data => ({ ...data, name: e.target.value }))} placeholder="Item name" />
          <input className="input-field" value={form.description} onChange={e => setForm(data => ({ ...data, description: e.target.value }))} placeholder="Description" />
          <input className="input-field" value={form.imageUrl} onChange={e => setForm(data => ({ ...data, imageUrl: e.target.value }))} placeholder="Image URL" />
          <div className="grid grid-cols-3 gap-2">
            <input className="input-field" type="number" value={form.price} onChange={e => setForm(data => ({ ...data, price: e.target.value }))} placeholder="Price" />
            <input className="input-field" value={form.category} onChange={e => setForm(data => ({ ...data, category: e.target.value }))} placeholder="Category" />
            <input className="input-field" type="number" step="0.1" value={form.rating} onChange={e => setForm(data => ({ ...data, rating: e.target.value }))} placeholder="Rating" />
          </div>
          <label className="flex items-center gap-2 text-sm font-semibold text-gray-600">
            <input type="checkbox" checked={form.isVeg} onChange={e => setForm(data => ({ ...data, isVeg: e.target.checked }))} />
            Vegetarian
          </label>
          <label className="flex items-center gap-2 text-sm font-semibold text-gray-600">
            <input type="checkbox" checked={form.isAvailable} onChange={e => setForm(data => ({ ...data, isAvailable: e.target.checked }))} />
            Available
          </label>
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
    <div className="grid grid-cols-[1fr_112px] gap-4 rounded-lg border border-gray-200 bg-white p-4 shadow-sm">
      <div className="min-w-0">
        <div className="mb-2 flex items-center gap-2">
          <span className={`h-3 w-3 rounded-full ${item.isVeg ? 'bg-green-600' : 'bg-red-600'}`} />
          <span className="text-xs font-semibold text-gray-500">{item.category}</span>
          {item.isPopular && <span className="rounded bg-orange-100 px-2 py-0.5 text-xs font-bold text-orange-700">Popular</span>}
          {!item.isAvailable && <span className="rounded bg-gray-100 px-2 py-0.5 text-xs font-bold text-gray-600">Unavailable</span>}
        </div>
        <h3 className="font-bold text-gray-950">{item.name}</h3>
        <p className="mt-1 line-clamp-2 text-sm text-gray-500">{item.description}</p>
        <div className="mt-3 flex items-center justify-between gap-3">
          <div className="flex items-center gap-3">
            <span className="font-bold">Rs {Number(item.price).toFixed(0)}</span>
            <span className="flex items-center gap-1 text-sm text-gray-500"><Star className="h-4 w-4 fill-yellow-400 text-yellow-400" />{item.rating}</span>
          </div>
          {canManage ? (
            <div className="flex items-center gap-2">
              <button type="button" onClick={() => setEditing(true)} className="rounded-md border border-gray-200 p-2 text-gray-600 hover:border-primary-300 hover:text-primary-600">
                <Pencil className="h-4 w-4" />
              </button>
              <button type="button" onClick={remove} className="rounded-md border border-red-200 p-2 text-red-600 hover:bg-red-50">
                <Trash2 className="h-4 w-4" />
              </button>
            </div>
          ) : (
            <button onClick={() => addItem(item, sourceRestaurant)} disabled={!item.isAvailable} className="inline-flex items-center gap-1 rounded-md bg-orange-600 px-3 py-2 text-sm font-bold text-white hover:bg-orange-700 disabled:cursor-not-allowed disabled:bg-gray-300">
              <Plus className="h-4 w-4" /> Add
            </button>
          )}
        </div>
      </div>
      <img src={item.imageUrl} alt={item.name} className="h-28 w-28 rounded-md object-cover" />
    </div>
  )
}
