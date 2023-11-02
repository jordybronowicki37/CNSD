from ..model.album import Album
from flask import current_app
from .. import db

def get_all_albums():
    """Fetch the list of all albums from the database"""
    return Album.query.all()

def same_album_with_different_id_exists(album):
    similar_album = Album.query.filter_by(name=album.name, artist=album.artist).one_or_none()
    return (similar_album is not None) and (similar_album.id != album.id)

def find_by_id(id):
    """fetch the album with the given id from the database"""
    album = Album.query.filter_by(id=id).one_or_none()
    if not album is None:
        # detach the object from the Flask-SQLAlchemy session (to be sure that change to the object are not persisted by mistake)
        db.session.expunge(album)
    current_app.logger.debug(f'Found: {album}')
    return album

def update_album(album):
    """update album information in the database"""
    db.session.merge(album)
    current_app.logger.debug(f'Objects to be updated: {db.session.dirty}')
    db.session.commit()
    return album

def create_album(album):
    """persist a new album in the database"""
    db.session.add(album)
    current_app.logger.debug(f'Objects to be created: {db.session.new}')
    db.session.commit()
    return album

def delete_album(album):
    """delete a new album from the database"""
    db.session.delete(album)
    current_app.logger.debug(f'Objects to be deleted: {db.session.delete}')
    db.session.commit()
