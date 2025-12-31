local key = KEYS[1]
local capacity = tonumber(ARGV[1])
local refillRate = tonumber(ARGV[2])
local now = tonumber(ARGV[3])
local ttl = tonumber(ARGV[4])

local data = redis.call("HMGET", key, "tokens", "lastRefill")

local tokens = tonumber(data[1])
local lastRefill = tonumber(data[2])


if tokens == nil then
    tokens = capacity
    lastRefill = now
end

local delta = math.max(0, now - lastRefill)
local refill = delta * refillRate
tokens = math.min(capacity, tokens + refill)


if tokens < 1 then
    redis.call("HMSET", key, "tokens", tokens, "lastRefill", now)
    redis.call("EXPIRE", key, ttl)
    return 0
end


tokens = tokens - 1

redis.call("HMSET", key, "tokens", tokens, "lastRefill", now)
redis.call("EXPIRE", key, ttl)

return 1
