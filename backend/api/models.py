from django.db import models

# Create your models here.
class User(models.Model):
	username = models.CharField(max_length=100, unique=True, primary_key=True)
	def __unicode__(self):
		return self.username
  
class Venue(models.Model):
  locu_id = models.CharField(unique=True, max_length=200)
  name = models.CharField(max_length=255)
  address = models.CharField(max_length=255)
  locality = models.CharField(max_length=50)
  region = models.CharField(max_length=5)
  website = models.CharField(max_length=255)
  lat = models.FloatField()
  lon = models.FloatField()
  def __unicode__(self):
		return self.name

class MenuItem(models.Model):
	locu_id = models.CharField(unique=True, max_length=200)
	venue = models.ForeignKey(Venue, related_name='menu_items')
	title = models.CharField(max_length=255)
	description = models.CharField(max_length=2048, null=True, blank=True)
	section = models.CharField(max_length=255, null=True, blank=True)
	price = models.CharField(max_length=10, null=True, blank=True)
	image_url = models.CharField(max_length=255, null=True, blank=True)
	def __unicode__(self):
		return self.title

class Rating(models.Model):
  user = models.ForeignKey(User)
  menu_item = models.ForeignKey(MenuItem, related_name='ratings')
  NOT_INTERESTED = 1
  DISLIKE = 2
  MAYBE = 3
  LIKE = 4
  VALUE_CHOICES = (
    (NOT_INTERESTED, 'Not interested'),
		(DISLIKE, 'Would not eat again'),
    (MAYBE, 'Maybe'),
		(LIKE, 'Would eat again'),
    )
  value = models.IntegerField(choices=VALUE_CHOICES)
