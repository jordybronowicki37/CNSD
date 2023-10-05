import {renderHook, waitFor} from "@testing-library/react";
import configureStore from 'redux-mock-store';
import {useCheckForUserAlreadyLoggedIn, useCheckForUserNotLoggedIn} from "./CheckLoginHook";
import {Provider} from "react-redux";
import reactRouterDom from 'react-router-dom';

jest.mock('react-router-dom');

let pushMock;
const mockNoUserState = {
    user: null
}
const mockUserState = {
    user: {
        id: 1,
        bsn: "",
        naam: "",
    }
}

function mockBasicApp(storeState) {
    const mockStore = configureStore([]);
    const store = mockStore(storeState);
    return ({ children }) => <Provider store={store}>{children}</Provider>
}

beforeEach(() => {
    pushMock = jest.fn();
    reactRouterDom.useHistory = jest.fn().mockReturnValue({push: pushMock});
})

test("useCheckForUserNotLoggedIn does not redirects when the user is logged in", async () => {
    const wrapper = mockBasicApp(mockUserState);

    renderHook(() => useCheckForUserNotLoggedIn(), {wrapper});

    await waitFor(() => {
        expect(pushMock).toHaveBeenCalledTimes(0);
    });
});

test("useCheckForUserNotLoggedIn redirects to the login page when the user is not logged in", async () => {
    const wrapper = mockBasicApp(mockNoUserState);

    renderHook(() => useCheckForUserNotLoggedIn(), {wrapper});

    await waitFor(() => {
        expect(pushMock).toHaveBeenCalledTimes(1);
        expect(pushMock).toHaveBeenCalledWith("/login");
    });
});

test("useCheckForUserAlreadyLoggedIn checks if the user is already logged in and redirects to the overview page", async () => {
    const wrapper = mockBasicApp(mockUserState);

    renderHook(() => useCheckForUserAlreadyLoggedIn(), {wrapper});

    await waitFor(() => {
        expect(pushMock).toHaveBeenCalledTimes(1);
        expect(pushMock).toHaveBeenCalledWith("/overview");
    });
});

test("useCheckForUserAlreadyLoggedIn checks if the user is already logged in, if not it doesn't redirect", async () => {
    const wrapper = mockBasicApp(mockNoUserState);

    renderHook(() => useCheckForUserAlreadyLoggedIn(), {wrapper});

    await waitFor(() => {
        expect(pushMock).toHaveBeenCalledTimes(0);
    });
});
