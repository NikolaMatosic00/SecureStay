import React from 'react';
import './Navbar.css'
import { useNavigate } from 'react-router-dom';

export const Navbar = () => {
  const navigate = useNavigate();
  const signOut = () => {
    sessionStorage.clear();
    navigate("/");
  }
  return (
    <div>
        <nav className='style'>
                <h4>Accommodation</h4>
                <button className='buttonStyle' onClick={() => signOut()}>Sign Out</button>
        </nav>
    </div>
  )
}
