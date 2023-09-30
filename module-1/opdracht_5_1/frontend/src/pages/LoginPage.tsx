import "./LoginPage.scss";

export function LoginPage() {
    return (
        <div className="login-page">
            <div className="login-card">
              <h2>Login</h2>
              <p>Login to continue</p>

              <form>
                <div className="input-container">
                  <label htmlFor="username">Username</label>
                  <input id="username" type="text"/>
                </div>
                <div className="input-container">
                  <label htmlFor="password">Password</label>
                  <input id="password" type="password"/>
                </div>
                <button type="submit">Login</button>
              </form>
            </div>
        </div>
    );
}
