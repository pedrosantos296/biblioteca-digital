import os
import requests
from flask import Flask, render_template, request, redirect, url_for

app = Flask(__name__)

# URL do book-service — usa variável de ambiente (Docker) ou localhost
API_BASE = os.getenv("API_BASE", "http://localhost:8080")


@app.route("/")
def index():
    query = request.args.get("q", "")

    if query:
        resp = requests.get(
            f"{API_BASE}/api/books/search",
            params={"query": query}
        )
    else:
        resp = requests.get(f"{API_BASE}/api/books")

    books = resp.json() if resp.ok else []

    return render_template("index.html", books=books, query=query)


@app.route("/book/<int:book_id>")
def book_detail(book_id):
    book = requests.get(f"{API_BASE}/api/books/{book_id}").json()
    reviews = requests.get(
        f"{API_BASE}/api/books/{book_id}/reviews"
    ).json()

    return render_template(
        "book.html",
        book=book,
        reviews=reviews
    )


@app.route("/add", methods=["GET", "POST"])
def add_book():
    if request.method == "POST":
        payload = {
            "title": request.form["title"],
            "author": request.form["author"],
            "isbn": request.form.get("isbn", ""),
            "year": int(request.form.get("year", 0)),
            "genre": request.form.get("genre", "")
        }

        requests.post(
            f"{API_BASE}/api/books",
            json=payload
        )

        return redirect(url_for("index"))

    return render_template("add_book.html")


@app.route("/book/<int:book_id>/review", methods=["POST"])
def add_review(book_id):
    payload = {
        "reviewer": request.form["reviewer"],
        "rating": int(request.form["rating"]),
        "comment": request.form["comment"]
    }

    requests.post(
        f"{API_BASE}/api/books/{book_id}/reviews",
        json=payload
    )

    return redirect(url_for("book_detail", book_id=book_id))


if __name__ == "__main__":
    app.run(host="0.0.0.0", port=5000, debug=True)