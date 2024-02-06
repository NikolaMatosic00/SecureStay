import './App.css';
import { SignUp } from './components/SignUp';
import {SignIn} from './components/SignIn';
import { BrowserRouter as Router, Route, Routes} from 'react-router-dom';

function App() {
  return (
    <div className="app">
      <div className="row justify-content-center align-items-center">
        <div className="col-md-auto">
          <Router>
            <div>
              <Routes>
                <Route path='/register' element={<SignUp />} />
                <Route path='/' element={<SignIn />} />
              </Routes>
            </div>
          </Router>
        </div>
      </div>
    </div>
  );
}

export default App;
