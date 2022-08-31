import AxiosService from "./AxiosService";

const instance = AxiosService.getInstance();

export const getAllPrograms = () => {
    return instance.get(`program/all`);
}

export const getProgramById = (id) => {
    return instance.get(`program/${id}`)

}

export function createProgram(programName) {
    return instance.post("program/new", {
        programName
    });
}