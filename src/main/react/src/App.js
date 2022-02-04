import React, {Component} from 'react';
import './App.css';
import './spinner.css';

function Header(props) {
    return (
        <header className="App-header">
            <div className="App-brand-container">
                <div className="App-brand-no"><a href="https://digdir.no">Digitaliseringsdirektoratet</a></div>
                <div className="App-brand-en">Norwegian Digitalisation Agency</div>
            </div>
            <div className="App-title">ID-porten OIDC client example</div>
            <div className="App-buttons">
                <button className="App-button" disabled={props.isLoggedIn} onClick={() => {window.location = "/oauth2/authorization/idporten"}}>Login</button>
                <button className="App-button" disabled={!props.isLoggedIn} onClick={() => {window.location = "/logout"}}>Logout</button>
            </div>
        </header>
    );
}

function LogEntry(props) {
    return (
        <div className="App-logentry">
            <div><strong>eid:</strong> {props.entry.eid}</div>
            <div><strong>integrasjon_id:</strong> {props.entry.integrasjon_id}</div>
            <div><strong>orgno:</strong> {props.entry.orgno}</div>
            <div><strong>tidspunkt:</strong> {props.entry.tidspunkt}</div>
            <div><strong>tjeneste:</strong> {props.entry.tjeneste}</div>
        </div>
    );
}

function Spinner() {
    return <div className="lds-ring"><div></div><div></div><div></div><div></div></div>;
}

class App extends Component {

    constructor(props) {
        super(props);
        this.state = {
            loading: false,
            isLoggedIn: false,
            data: []
        };
    }

    componentDidMount() {
        this.checkAuthenticated();
    }


    checkAuthenticated() {
        fetch("/api/authcheck", {mode: 'cors', credentials: 'include', headers: {'Content-Type': 'application/json'}})
            .then(response => {
                this.setState({isLoggedIn: !!response.ok});
            })
            .catch(err => {/* do nothing... */});
    }

    fetchList() {
        this.setState({loading: true, data: []});
        fetch("/api/userlog", {mode: 'cors', credentials: 'include', headers: {'Content-Type': 'application/json'}})
            .then(response => {
                if(response.ok) {
                    response.json().then(data => this.setState({data}));
                }
            })
            .finally(() => this.setState({loading: false}));
    }

    render() {

        return (
            <div className="App-container">
                <main>
                    <Header isLoggedIn={this.state.isLoggedIn} isLoading={this.state.loading} />
                    <button className="App-button" disabled={this.state.loading || !this.state.isLoggedIn} onClick={this.fetchList.bind(this)}>Fetch userlog</button>
                    {this.state.loading && <Spinner />}
                    <div className="App-logentry-container">
                    {this.state.data.map((item, index) => <LogEntry key={index} entry={item} />)}
                    </div>

                </main>
            </div>
        );
    }
}

export default App;
