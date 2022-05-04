import { useNavigate } from "react-router-dom";

const Unauthorized = () => {
    const navigate = useNavigate();
    const goBack = () => navigate(-1);

    return (
        <section>
            <h1>Нет доступа</h1>
            <br />
            <p>У вас недостаточно прав для просмотра этой страницы</p>
            <div className="flexGrow">
                <button onClick={goBack}>Назад</button>
            </div>
        </section>
    )

}

export default Unauthorized;