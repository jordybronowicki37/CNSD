from app.main.model.track import Track
from flask import current_app
from app.main.model import db

def find_album_tracks(album):
    # Create and execute an SQLAlchemy query to retrieve all the tracks for a given album
    # use the filter_by() and all() methods of the Query API (https://docs.sqlalchemy.org/en/13/orm/query.html)
    # see also https://docs.sqlalchemy.org/en/13/orm/tutorial.html#querying
    # Note that Track.query produce a query object equivalent to db.session.query(Track)
    #
    # return the list of tracks found
    # before returning the tracks, call the db.session.expunge_all() method to make sure that the tracks
    # are detached from the session.
    return NotImplemented

def same_track_with_different_id_exists(track):
    similar_track = Track.query.filter_by(albumid=track.albumid, tracknr=track.tracknr).one_or_none()
    return (similar_track is not None) and (similar_track.trackid != track.trackid)

def find_track_by_id(id):
    """Find and return a track in the database with the given id"""
    track = Track.query.get(id)
    # detach the object from the Flask-SQLAlchemy session (to be sure that change to the object are not persisted by mistake)
    if not track is None:
        db.session.expunge(track)
    return track

def add_track(track):
    """Persist a new track in the database, return the added track"""
    # Implement this method by using the add() and commit() method of the db.session object
    return NotImplemented

def delete_track(track):
    """Delete a track in the database"""
    db.session.delete(track)
    current_app.logger.debug(f'Objects to be deleted: {db.session.delete}')
    db.session.commit()

def update_track(track):
    """Update track information in the database, return the updated track"""
    db.session.merge(track)
    current_app.logger.debug(f'Objects to be updated: {db.session.dirty}')
    db.session.commit()
    return track
