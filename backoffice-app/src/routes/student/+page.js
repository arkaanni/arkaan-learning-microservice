export async function load({ fetch }) {
    try {
        const students = await fetch('http://127.0.0.1:8443/students', {
            headers: {
                'Accept': 'application/json',
            }
        })
        return {
            students: students.json()
        }
    } catch (error) {
        throw Error("Oopps..")
    }
}