#import SQLAlchemy instance created in the __init__.py file
from app.main.model import db

from sqlalchemy import Column, Integer, String, ForeignKey, CheckConstraint
from sqlalchemy.orm import relationship

class Track(db.Model):
    __tablename__ = 'albumtracks'

    trackid = Column(Integer, primary_key=True)
    name = Column(String(100))
    tracknr = Column(Integer, CheckConstraint('tracknr > 0'))
    albumid = Column(Integer, ForeignKey('album.id'))
    album = relationship('Album', back_populates='tracks')

    def __repr__(self):
        return f'<Track name:{self.name} number:{self.tracknr} - {self.albumid}>'
