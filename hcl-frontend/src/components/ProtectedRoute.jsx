import React from 'react'
import { Navigate } from 'react-router-dom'
import { useAuth } from '../context/AuthContext'

export default function ProtectedRoute({ children, role }) {
  const { token, user, loading } = useAuth()
  if (loading) return <div className="min-h-screen grid place-items-center"><div className="h-10 w-10 rounded-full border-4 border-orange-200 border-t-orange-600 animate-spin" /></div>
  if (!token) return <Navigate to="/login" replace />
  if (role && user?.role !== role) return <Navigate to={user?.role === 'ADMIN' ? '/admin' : '/'} replace />
  return children
}
