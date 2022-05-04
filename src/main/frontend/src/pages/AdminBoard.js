import { Link } from "react-router-dom";
import Users from "../components/Users";

const AdminBoard = () => {
    return(
        <section>
            <h1>Администрирование</h1>
            <br/>
            <Users />
            <br/>
            <div className="flexGrow">
                <Link to="/">Главная</Link>
            </div>
        </section>
    )
}

export default AdminBoard;