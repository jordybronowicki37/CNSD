#import SQLAlchemy instance created in the __init__.py file
from app.main.model import db

from app.main.model.track import Track
from sqlalchemy.orm import relationship
from sqlalchemy import Column, Integer, String, CheckConstraint

class Album(db.Model):
    __tablename__ = 'album'

    id = Column(Integer, primary_key=True, nullable=False)
    name = Column(String(100), nullable=False)
    artist = Column(String(100), nullable=False)
    year = Column(Integer, CheckConstraint('year >= 0 AND year <= 3000'))
    tracks = relationship('Track', order_by=Track.tracknr, back_populates='album', cascade='all')

    def __repr__(self):
        return f'<Album name:"{self.name}" artist:"{self.artist}" year:{self.year}>'
