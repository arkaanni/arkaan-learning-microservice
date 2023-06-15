export async function load({ fetch }) {
    try {
        const subjects = await fetch('http://127.0.0.1:8008/subject', {
            headers: {
                'Accept': 'application/json',
            }
        })
        return {
            subjects: subjects.json()
        }
    } catch (error) {
        throw error("Oopps..")
    }
}