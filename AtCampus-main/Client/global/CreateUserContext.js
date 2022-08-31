import React, {createContext, useState} from "react";


const CreateUserContext = createContext()

const CreateUserProvider = ({children}) => {
    const [firstname, setFirstname] = useState("")
    const [lastname, setLastname] = useState("")
    const [phoneNr, setPhoneNr] = useState()
    const [email, setEmail] = useState("")
    const [school, setSchool] = useState(1)
    const [program, setProgram] = useState(1)
    const [password, setPassword] = useState("")
    const [image, setImage] = useState("https://toppng.com/uploads/preview/app-icon-set-login-icon-comments-avatar-icon-11553436380yill0nchdm.png")

    return(
        <CreateUserContext.Provider value={{
            firstname, setFirstname,
            lastname, setLastname,
            phoneNr, setPhoneNr,
            email, setEmail,
            school, setSchool,
            program, setProgram,
            password, setPassword,
            image, setImage
        }}>
            {children}
        </CreateUserContext.Provider>
    )
}

export {CreateUserContext, CreateUserProvider}