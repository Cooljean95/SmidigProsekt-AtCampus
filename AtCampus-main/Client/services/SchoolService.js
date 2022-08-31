import AxiosService from "./AxiosService";

const instance = AxiosService.getInstance();

export const getAllSchools = () => {
    return instance.get(`school/all`);
}

export const getSchoolById = (id) => {
    return instance.get(`school/${id}`)

}

export function createSchool(schoolName) {
    return instance.post("school/new", {
        schoolName
    });
}
