FROM python:3.6

# We copy just the requirements.txt first to leverage Docker cache
COPY ./requirements.txt /app/requirements.txt

WORKDIR /app

RUN pip install -r requirements.txt

COPY . /app

RUN ["chmod", "+x", "/app/wait-for-it.sh"]

ENV FLASK_APP app.main:create_app()
ENV FLASK_RUN_HOST 0.0.0.0
ENV FLASK_RUN_PORT 8080

CMD [ "flask", "run"]