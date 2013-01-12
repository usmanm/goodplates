# Create your views here.
from django.http import HttpResponse, HttpResponseBadRequest
from api.models import User, MenuItem, Rating

import json

def error_json(message):
	return json.dumps({"error": message})

def user_json(registered):
	return json.dumps({"registered": registered}, indent=4)
def user(request):
	try:
		username = request.GET["username"]
		user = User.objects.get(username=username)
		return HttpResponse(user_json(registered=True))
	except User.DoesNotExist:
		return HttpResponse(user_json(registered=False))
	except KeyError:
		return HttpResponseBadRequest("username required but not provided")

def register_user(request):
	try:
		username = request.GET["username"]
	except KeyError:
		return HttpResponseBadRequest("username required but not provided")
	u = User(username=username)
	u.save()
	return HttpResponse(user_json(registered=True))

def rate(request):
	try:
		menu_item_id = request.GET["item"]
	except KeyError:
		return HttpResponseBadRequest(error_json('item required but not prodided'))
	try:
		username = request.GET["username"]
	except KeyError:
		return HttpResponseBadRequest(error_json('username required but not prodided'))
	try:
		rating = request.GET["rating"]
	except KeyError:
		return HttpResponseBadRequest(error_json('rating required but not prodided'))
	try:
		item = MenuItem.objects.get(locu_id=menu_item_id)
		user = User.objects.get(username=username)
	except MenuItem.DoesNotExist:
		return HttpResponseBadRequest(error_json('Menu item "%s" does not exist'%menu_item_id))
	except User.DoesNotExist:
		return HttpResponseBadRequest(error_json('User "%s" does not exist'%username))
	if rating.lower() == "like":
		r = Rating(user=user, menu_item=item, value=Rating.LIKE)
		r.save()
		return HttpResponse("")
	elif rating.lower() == "dislike":
		r = Rating(user=user, menu_item=item, value=Rating.DISLIKE)
		r.save()
		return HttpResponse("")
	else:
		return HttpResponseBadRequest(error_json('Rating "%s" is not valid'%rating))

def venue_to_json_dict(venue):
	return {"locu_id": venue.locu_id,
	        "name": venue.name,
	        "address": venue.address,
	        "locality": venue.locality,
	        "region": venue.region,
	        "website": venue.website}
def item_to_json_dict(item):
	return {"locu_id": item.locu_id,
	        "title": item.title,
	        "venue": venue_to_json_dict(item.venue),
	        "description": item.description,
	        "section": item.section,
	        "price": item.price,
	        "image_url": item.image_url}

def get_ranked_items(request):
	try:
		username = request.GET["username"]
	except KeyError:
		return HttpResponseBadRequest(error_json("username required but not provided"))
	try:
		user = User.objects.get(username=username)
	except User.DoesNotExist:
		return HttpResponseBadRequest(error_json('user "%s" not found in database'%username))
	lat = request.GET.get("lat", None)
	lon = request.GET.get("lon", None)
	if lat == None and lon != None:
		return HttpResponseBadRequest(error_json('lon parameter provided without lat parameter'))
	if lat != None and lon == None:
		return HttpResponseBadRequest(error_json('lat parameter provided without lon parameter'))
	if lat != None:
		try:
			lat = float(lat)
		except ValueError:
			return HttpResponseBadRequest(error_json('lat parameter invalid'))
		try:
			lon = float(lon)
		except ValueError:
			return HttpResponseBadRequest(error_json('lon parameter invalid'))
	page = int(request.GET.get("page", 1))
	count = int(request.GET.get("size", 50))

	max_distance = 10 #km
	from haversine import distance
	if lat != None:
		items = [ x for x in MenuItem.objects.all().select_related('venue') if distance((lat,lon), (x.venue.lat, x.venue.lon)) <= max_distance]
		#items = [ x for x in MenuItem.objects.all() if distance((lat,lon), (2, 2)) <= max_distance]
	else:
		items = [ x for x in MenuItem.objects.all() ]
	from random import shuffle
	shuffle(items)
	output = [ item_to_json_dict(i) for i in items[:count] ]
	return HttpResponse(json.dumps(output, indent=4))
