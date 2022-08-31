export default async function upload(photo) {
	const data = new FormData()
	data.append("image", photo)
	data.append("key", "3af58fd31427c1f3fe83ea8426ded6ab");

	const res = await fetch("https://api.imgbb.com/1/upload", {
		method: "post",
		body: data
	})

	const json = await res.json();

	return json?.data?.display_url
}
