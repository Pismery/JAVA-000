if redis.call("setnx", KEYS[1], KEYS[2]) == 1 then
    return redis.call("pexpire", KEYS[1], KEYS[3])
else
    return 0
end
          