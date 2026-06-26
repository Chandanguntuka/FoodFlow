import axios from 'axios'

const rawBase = import.meta.env.VITE_API_URL || import.meta.env.VITE_API_BASE_URL || 'https://foodflow-r55t.onrender.com/api'
const baseURL = rawBase.replace(/\/+$/, '').endsWith('/api')
  ? rawBase.replace(/\/+$/, '')
  : `${rawBase.replace(/\/+$/, '')}/api`

const api = axios.create({ baseURL, headers: { 'Content-Type': 'application/json' } })

api.interceptors.request.use((config) => {
  const token = localStorage.getItem('token')
  if (token) config.headers.Authorization = `Bearer ${token}`
  return config
})

api.interceptors.response.use(
  (response) => response,
  (error) => {
    if (error.response?.status === 401 && !String(error.config?.url || '').includes('/auth/')) {
      localStorage.removeItem('token')
      localStorage.removeItem('user')
      window.location.href = '/login'
    }
    return Promise.reject(error)
  }
)

const unwrapPage = (res) => ({ ...res, data: res.data?.content || res.data })

const restaurantPayload = (data) => ({
  name: data.name,
  cuisine: data.cuisine,
  location: data.location || data.address || 'Hyderabad',
  imageUrl: data.imageUrl || 'https://images.unsplash.com/photo-1540189549336-e6e99c3679fe?auto=format&fit=crop&w=400&q=80',
  rating: Number(data.rating || 4.2),
  deliveryTime: data.deliveryTime || '30-40 min',
  minOrder: Number(data.minOrder || 149),
  isOpen: data.isOpen ?? data.open ?? true,
})

const menuItemPayload = (data) => ({
  name: data.name,
  description: data.description || data.name,
  price: Number(data.price),
  imageUrl: data.imageUrl || 'https://images.unsplash.com/photo-1567188040759-fb8a883dc6d8?auto=format&fit=crop&w=400&q=80',
  category: data.category || 'Main Course',
  isVeg: data.isVeg ?? true,
  rating: Number(data.rating || 4.2),
  preparationTime: Number(data.preparationTime || 20),
  isPopular: data.isPopular || false,
  isAvailable: data.isAvailable ?? data.available ?? true,
})

export const authAPI = {
  login: (data) => api.post('/auth/login', data),
  register: (data) => api.post('/auth/register', data),
}

export const restaurantAPI = {
  getAll: (params = {}) => api.get('/restaurants', { params }).then(unwrapPage),
  getById: (id) => api.get(`/restaurants/${id}`),
  getMenu: (id) => api.get(`/restaurants/${id}/menu`),
  create: (data) => api.post('/admin/restaurants', restaurantPayload(data)),
  update: (id, data) => api.put(`/admin/restaurants/${id}`, restaurantPayload(data)),
  delete: (id) => api.delete(`/admin/restaurants/${id}`),
}

export const orderAPI = {
  place: (data) => api.post('/orders', data),
  placeOrder: (data) => api.post('/orders', data),
  mine: () => api.get('/orders/my'),
  getHistory: () => api.get('/orders/my'),
  get: (id) => api.get(`/orders/${id}`),
  getById: (id) => api.get(`/orders/${id}`),
  updateStatus: (id, status) => api.put(`/orders/${id}/status`, { status }),
  cancelOrder: (id) => api.put(`/orders/${id}/status`, { status: 'CANCELLED' }),
}

export const paymentAPI = {
  dummy: (data) => api.post('/payment/dummy', data),
}

export const menuItemAPI = {
  getById: (id) => api.get(`/menu-items/${id}`),
  create: (data) => api.post(`/admin/restaurants/${data.restaurantId}/menu`, menuItemPayload(data)),
  update: (id, data) => api.put(`/admin/menu/${id}`, menuItemPayload(data)),
  delete: (id) => api.delete(`/admin/menu/${id}`),
}

export const adminAPI = {
  stats: () => api.get('/admin/stats'),
  createRestaurant: (data) => api.post('/admin/restaurants', restaurantPayload(data)),
  updateRestaurant: (id, data) => api.put(`/admin/restaurants/${id}`, restaurantPayload(data)),
  deleteRestaurant: (id) => api.delete(`/admin/restaurants/${id}`),
  createMenuItem: (restaurantId, data) => api.post(`/admin/restaurants/${restaurantId}/menu`, menuItemPayload(data)),
  updateMenuItem: (itemId, data) => api.put(`/admin/menu/${itemId}`, menuItemPayload(data)),
  deleteMenuItem: (itemId) => api.delete(`/admin/menu/${itemId}`),
  orders: () => api.get('/admin/orders'),
  updateOrderStatus: (id, status) => api.put(`/admin/orders/${id}/status`, { status }),
}

export default api
