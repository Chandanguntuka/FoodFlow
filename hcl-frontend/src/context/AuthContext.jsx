import React, { createContext, useContext, useEffect, useState } from 'react'
import toast from 'react-hot-toast'
import { authAPI } from '../services/api'

const AuthContext = createContext(null)

export function AuthProvider({ children }) {
  const [user, setUser] = useState(null)
  const [token, setToken] = useState(localStorage.getItem('token'))
  const [loading, setLoading] = useState(true)

  useEffect(() => {
    const saved = localStorage.getItem('user')
    if (saved && token) setUser(JSON.parse(saved))
    setLoading(false)
  }, [token])

  const persist = (data) => {
    setToken(data.token)
    setUser(data.user)
    localStorage.setItem('token', data.token)
    localStorage.setItem('user', JSON.stringify(data.user))
  }

  const login = async (email, password) => {
    try {
      const res = await authAPI.login({ email, password })
      persist(res.data)
      toast.success(`Welcome back, ${res.data.user.name}`)
      return { success: true, user: res.data.user }
    } catch (error) {
      const message = error.response?.data?.message || 'Login failed'
      toast.error(message)
      return { success: false, message }
    }
  }

  const register = async (payload) => {
    try {
      const role = payload.role === 'CUSTOMER' ? 'USER' : (payload.role || 'USER')
      const res = await authAPI.register({ ...payload, role })
      persist(res.data)
      toast.success('Account created')
      return { success: true, user: res.data.user }
    } catch (error) {
      const message = error.response?.data?.message || 'Registration failed'
      toast.error(message)
      return { success: false, message }
    }
  }

  const logout = () => {
    setUser(null)
    setToken(null)
    localStorage.removeItem('token')
    localStorage.removeItem('user')
  }

  return <AuthContext.Provider value={{ user, token, loading, login, register, logout }}>{children}</AuthContext.Provider>
}

export const useAuth = () => {
  const context = useContext(AuthContext)
  if (!context) throw new Error('useAuth must be used within AuthProvider')
  return context
}
