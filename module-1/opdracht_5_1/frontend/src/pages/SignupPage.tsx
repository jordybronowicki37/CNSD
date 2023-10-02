import "./SignupPage.scss";
import {useHistory} from "react-router-dom";
import {ChangeEvent, FormEvent, useState} from "react";
import {useCheckForUserAlreadyLoggedIn} from "../hooks/CheckLoginHook.ts";

export function SignupPage() {
  useCheckForUserAlreadyLoggedIn();
  const history = useHistory();

  const [formData, setFormData] = useState({
    username: "",
    iban: "",
    password: "",
    password2: "",
  });

  const handleInputChange = (e: ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: value });
  };

  const handleSubmit = (event: FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    history.push("/overview");
  }

  return (
    <div className="signup-page">
      <div className="signup-card">
        <h2>Signup</h2>
        <p>Signup to continue</p>

        <form onSubmit={handleSubmit}>
          <div className="input-container">
            <label htmlFor="username">Username</label>
            <input type="text" id="username" name="username" value={formData.username} onChange={handleInputChange}/>
          </div>

          <div className="input-container">
            <label htmlFor="iban">Iban</label>
            <input type="text" id="iban" name="iban" minLength={9} maxLength={9} value={formData.iban} onChange={handleInputChange}/>
          </div>

          <div className="input-container">
            <label htmlFor="password">Password</label>
            <input type="password" id="password" name="password" value={formData.password} onChange={handleInputChange}/>
          </div>

          <div className="input-container">
            <label htmlFor="password2">Repeat password</label>
            <input type="password" id="password2" name="password2" value={formData.password2} onChange={handleInputChange}/>
          </div>
          <button type="submit">Signup</button>
        </form>
      </div>
    </div>
  )
}