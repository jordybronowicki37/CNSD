import "./Header.scss";
import {useDispatch, useSelector} from "react-redux";
import {StoreTypes} from "../data/DataStore.ts";
import {userLogoutAction} from "../data/reducers/UserReducer.ts";
import {Link, useLocation} from "react-router-dom";

export function Header() {
  const user = useSelector<StoreTypes, StoreTypes["user"]>(s => s.user);
  const dispatch = useDispatch();
  const {pathname} = useLocation();

  const generateLinkParams = (to: string) => {
    return {
      to,
      className: `${pathname === to ? "current-location" : ""}`
    }
  }

  return (
    <header>
      <h1>CNSD Banking Service</h1>

      {user === null ?
        <nav>
          <Link {...generateLinkParams("/signup")}>Signup</Link>
          <Link {...generateLinkParams("/login")}>Login</Link>
        </nav> :
        <nav>
          <Link {...generateLinkParams("/overview")}>Overview</Link>
          <div className="current-user">
            <p>{user.username}</p>
            <Link {...generateLinkParams("/login")} onClick={() => dispatch(userLogoutAction())}>Logout</Link>
          </div>
        </nav>
      }
    </header>
  );
}