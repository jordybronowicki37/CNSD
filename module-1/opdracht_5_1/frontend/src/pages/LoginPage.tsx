import "./LoginPage.scss";
import {ChangeEvent, FormEvent, useState} from "react";
import {useHistory} from "react-router-dom";
import {loginUser} from "../data/requesters/UserRequester.ts";
import {useCheckForUserAlreadyLoggedIn} from "../hooks/CheckLoginHook.ts";

export function LoginPage() {
  useCheckForUserAlreadyLoggedIn();
  const history = useHistory();
  const [loading, setLoading] = useState(false);
  const [loginFailed, setLoginFailed] = useState(false);
  const [formData, setFormData] = useState({
    username: "",
    password: "",
  });

  const handleInputChange = (e: ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: value });
  };

  const handleSubmit = (event: FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    setLoading(true)
    loginUser(formData.username, formData.password)
        .then(_ => history.push("/overview"))
        .catch(_ => {
          setLoading(false);
          setLoginFailed(true);
        });
  }

  return (
    <div className="login-page">
      <div className="login-card">
        <h2>Login</h2>
        <p>Login to continue</p>
        <p hidden={!loginFailed} className="login-failed-message">Username or password incorrect!</p>
        <form onSubmit={handleSubmit}>
          <div className="input-container">
            <label htmlFor="username">Username</label>
            <input
                type="text"
                id="username"
                name="username"
                value={formData.username}
                onChange={handleInputChange}
                disabled={loading}/>
          </div>
          <div className="input-container">
            <label htmlFor="password">Password</label>
            <input
                type="password"
                id="password"
                name="password"
                value={formData.password}
                onChange={handleInputChange}
                disabled={loading}/>
          </div>
          <button type="submit">Login</button>
        </form>
      </div>
    </div>
  );
}
