# Mit docker build -t hsmrt_mailtrap . (mit .) bauen
# Mit docker run -d --name=mailtrap -p 80:80 -p 25:25  -p 110:110 hsmrt_mailtrap laufen lassen
FROM eaudeweb/mailtrap

RUN apt-get -y upgrade
RUN apt-get -y update

RUN apt-get -y install dovecot-pop3d
RUN sed -i "s/#disable_plaintext_auth = yes/disable_plaintext_auth = no/" /etc/dovecot/conf.d/10-auth.conf
RUN echo "listen = *" >> /etc/dovecot/conf.d/25-custom-stats.conf

EXPOSE 25 80 110

ENTRYPOINT /var/local/docker-entrypoint.sh
