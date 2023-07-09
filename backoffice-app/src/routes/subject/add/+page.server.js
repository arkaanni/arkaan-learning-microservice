export const actions = {
    default: async ({ request }) => {
        const formData = Object.fromEntries(await request.formData())
        try {
            const response = await fetch('http://127.0.0.1:8008/subject', {
                method: 'post',
                headers: {
                    'Content-Type': 'application/json',
                    'Accept': 'text/html'
                },
                body: JSON.stringify(formData)
            })
            return {
                success: response.status == 200 ? true : false,
                msg: await response.text(),
            }
        } catch (e) {
            return {
                success: false,
                msg: 'Something went wrong',
            }
        }
    }
}